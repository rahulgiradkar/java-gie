package javagie.services;

import java.util.List;
import javagie.entities.Recurso;
import javagie.entities.Reserva;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author eduardo
 */
@Service
public class ReservaService {

    @Transactional(readOnly=true)
    public List<Reserva> traerReservas(Recurso recursoSelected) {
        return null;
    }
    
    
    
}
