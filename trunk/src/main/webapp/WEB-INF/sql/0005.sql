--region
create table region (
   id_region    int4    not null,
   nombre   varchar(100) null
);

alter table region
   add constraint pk_region primary key (id_region);

--provincia
create table provincia (
   id_provincia         int4                 not null,
   id_region            int4                 null,
   nombre               varchar(100)          null
);

alter table provincia
   add constraint pk_provincia primary key (id_provincia);

alter table provincia
   add constraint fk_provinci_reference_region foreign key (id_region)
      references region (id_region)
      on delete restrict on update restrict;

--comuna
create table comuna (
   id_comuna            int4                 not null,
   id_provincia         int4                 null,
   nombre               varchar(50)          null
);

alter table comuna
   add constraint pk_comuna primary key (id_comuna);

alter table comuna
   add constraint fk_comuna_reference_provinci foreign key (id_provincia)
      references provincia (id_provincia)
      on delete restrict on update restrict;

--institucion
create table institucion (
   id_institucion       bigserial            not null,
   nombre               varchar(100)         not null,
   fono_contacto        varchar(100)         not null
);

alter table institucion
   add constraint pk_institucion primary key (id_institucion);

--empresa
create table empresa (
   id_empresa           bigserial            not null,
   nombre               varchar(100)         not null,
   fono_contacto        varchar(100)         not null
);

alter table empresa
   add constraint pk_empresa primary key (id_empresa);

--historial profesional
create table historial_profesional (
   id_usuario           integer              not null,
   id_empresa           bigint               not null
);

alter table historial_profesional
   add constraint pk_historial_profesional primary key (id_empresa, id_usuario);

alter table historial_profesional
   add constraint fk_historia_reference_empresa foreign key (id_empresa)
      references empresa (id_empresa)
      on delete restrict on update restrict;

alter table historial_profesional
   add constraint fk_historia_reference_usuario foreign key (id_usuario)
      references usuario (id_usuario)
      on delete restrict on update restrict;

--historial academico
create table historial_academico (
   id_usuario           integer              not null,
   id_institucion       bigint               not null
);

alter table historial_academico
   add constraint pk_historial_academico primary key (id_usuario, id_institucion);

alter table historial_academico
   add constraint fk_historia_reference_usuario foreign key (id_usuario)
      references usuario (id_usuario)
      on delete restrict on update restrict;

alter table historial_academico
   add constraint fk_historia_reference_instituc foreign key (id_institucion)
      references institucion (id_institucion)
      on delete restrict on update restrict;

--imagen usuario
create table imagen_perfil (
   id_usuario     integer                 not null,
   bytes_imagen         bytea                null
);

alter table imagen_perfil
   add constraint pk_imagen_perfil primary key (id_usuario);

alter table imagen_perfil
   add constraint fk_imagen_p_reference_usuario foreign key (id_usuario)
      references usuario (id_usuario)
      on delete restrict on update restrict;

--cambios tabla usuario
ALTER TABLE usuario ADD COLUMN direccion_actual_id_comuna integer;
ALTER TABLE usuario ADD COLUMN direccion_actual_calle varchar(300);
ALTER TABLE usuario ADD COLUMN fono_fijo varchar(50);
ALTER TABLE usuario ADD COLUMN fono_movil varchar(50);
ALTER TABLE usuario ADD COLUMN fecha_nacimiento date;

alter table usuario
   add constraint fk_usuario_reference_comuna foreign key (direccion_actual_id_comuna)
      references comuna (id_comuna)
      on delete restrict on update restrict;



