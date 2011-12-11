package javagie.arquitectura;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AutoParcheListener implements ServletContextListener {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private Connection con;
	private DataSource dataSource;
	private File directorioParches;
	private List<Parche> parches;
	private String regex = "[0-9]*.sql";

	public void contextDestroyed(ServletContextEvent event) {

	}

	public void contextInitialized(ServletContextEvent event) {
		try {
			log.info("----- comenzo proceso de parches ---");
			dataSource = (DataSource) WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()).getBean("dataSource");
			con = dataSource.getConnection();
			directorioParches = new File(event.getServletContext().getRealPath((event.getServletContext().getInitParameter("directorio-parches"))));
			// inicio proceso de parches
			if (!estaLaTablaDeParches(con))
				crearTablaDeParches(con);

			cargarParchesDesdeDirectorio();

			boolean corte;
			for (Parche p : parches) {
				corte = true;
				if (p.getNombre().matches(regex)) {
					try {
						ejecutarSQL(p);
					} catch (Exception e) {
						corte = false;
					}

					while (corte && !fueAplicado(con, p)) {
					}

					if (!corte)
						break;
				}
			}
			log.info("---- termino proceso -----------");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
                    try {
                        if(con!=null)con.close();
                    }catch(Exception ex) {ex.printStackTrace();}
                }
	}

	/**
	 * METODOS PRIVADOS
	 */

	private void ejecutarSQL(Parche p) throws Exception {
		String nombre = p.getNombre();
		final class SqlExecuter extends SQLExec {

			private Connection con;

			public Connection getConnection() {
				return con;
			}

			public SqlExecuter(Connection con) {
				this.con = con;
				Project project = new Project();
				project.init();

				setProject(project);
				setTaskType("sql");
				setTaskName("sql");
				setEncoding("UTF-8");
			}
		}
		Connection conexionParche = null;
		try {
			if (!fueAplicado(con, p)) {
				conexionParche = dataSource.getConnection();
				conexionParche.setAutoCommit(false);
				SqlExecuter executer = new SqlExecuter(conexionParche);
				executer.setSrc(new File(directorioParches + "/" + nombre));
				executer.execute();

				marcarParcheComoAplicado(con, p);
			}
		} catch (Exception e) {
			log.error("ERROR: {}", nombre);
			e.printStackTrace();
			if(conexionParche != null) conexionParche.rollback();
			throw e;
		} finally {
                    if(conexionParche != null && conexionParche.isClosed() ==false) {
                        try {conexionParche.close();}catch(Exception ex) {ex.printStackTrace();}
                    }
                }
	}

	private boolean fueAplicado(Connection connection, Parche parche)

	throws SQLException {
		PreparedStatement pstmt = connection.prepareStatement("SELECT count(name) FROM sql_patches WHERE name like ?");
		pstmt.setString(1, parche.getNombre());
		ResultSet rs = pstmt.executeQuery();

		rs.next();
		int contador = rs.getInt(1);
		rs.close();
		pstmt.close();
		if (contador > 0) {
			return true;
		} else {
			return false;
		}

	}

	private void marcarParcheComoAplicado(Connection connection, Parche parche) {

		try {
			log.info("Marcando como aplicado el parche: {}", parche.getNombre());
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO sql_patches VALUES(?)");

			pstmt.setString(1, parche.getNombre());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cargarParchesDesdeDirectorio() {
		List<Parche> retorno = new Vector<Parche>();

		try {
			for (File file : directorioParches.listFiles()) {
				retorno.add(new Parche(file));
			}

		} catch (Exception e) {
			log.error("Ocurrio un error al buscar los archivos SQL: {}", e

			.getMessage());
			e.printStackTrace();
		}
		Collections.sort(retorno);
		this.parches = retorno;
	}

	public static boolean estaLaTablaDeParches(Connection connection) throws SQLException {

		Statement stmt = connection.createStatement();
		try {
			stmt.executeQuery("SELECT * FROM sql_patches");
			return true;
		} catch (Throwable e) {

		} finally {
			try {
				stmt.close();

			} catch (Throwable e) {

			}
		}
		return false;
	}

	public static void crearTablaDeParches(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();

		String sql = "CREATE TABLE sql_patches (name VARCHAR(50) NOT NULL, CONSTRAINT PK_PATCHES PRIMARY KEY (name))";
		stmt.execute(sql);
		stmt.close();
	}

	public class Parche implements Comparable<Parche> {

		private String nombre;
		private File file;

		public Parche(File file) {
			this.nombre = file.getName();
			this.file = file;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public int compareTo(Parche parche) {

			return this.getNombre().compareToIgnoreCase(parche.getNombre());
		}

	};

}