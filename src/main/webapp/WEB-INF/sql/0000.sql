CREATE TABLE revinfo
(
  rev integer NOT NULL,
  revtstmp bigint,
  CONSTRAINT revinfo_pkey PRIMARY KEY (rev)
);

CREATE SEQUENCE hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

create table horas_soporte (
   id_tipo_cargo        int4                 not null,
   id_proyecto          int4                 not null,
   horas_hombres        int4                 null
);

alter table horas_soporte
   add constraint pk_horas_soporte primary key (id_tipo_cargo, id_proyecto);

create table participante (
   id_participante      serial               not null,
   id_usuario           int4                 not null,
   id_proyecto          int4                 not null,
   id_tipo_cargo        int4                 null
);

alter table participante
   add constraint pk_participante primary key (id_participante);

create  index participante_index on participante (
id_usuario,
id_proyecto,
id_tipo_cargo
);

create table proyecto (
   id_proyecto          serial               not null,
   id_tipo_proyecto     int4                 not null,
   id_tipo_estado_proyecto int4                 null,
   nombre               varchar(100)         null,
   descripcion          varchar(500)         null,
   fecha_inicio         timestamp with time zone null
);

alter table proyecto
   add constraint pk_proyecto primary key (id_proyecto);

create table tipo_cargo (
   id_tipo_cargo        int4                 not null,
   nombre               varchar(50)          null
);

alter table tipo_cargo
   add constraint pk_tipo_cargo primary key (id_tipo_cargo);

create table tipo_estado_proyecto (
   id_tipo_estado_proyecto int4                 not null,
   nombre               varchar(50)          not null
);

alter table tipo_estado_proyecto
   add constraint pk_tipo_estado_proyecto primary key (id_tipo_estado_proyecto);

create table tipo_proyecto (
   id_tipo_proyecto     int4                 not null,
   nombre               varchar(50)          null
);

alter table tipo_proyecto
   add constraint pk_tipo_proyecto primary key (id_tipo_proyecto);

create table usuario (
   id_usuario           serial               not null,
   apellidos            varchar(100)         null,
   nombres              varchar(100)         null,
   email                varchar(50)          not null,
   password             varchar(500)         null,
   es_admin             bool                 not null
);

alter table usuario
   add constraint pk_usuario primary key (id_usuario);

create unique index usuario_email_key on usuario (
email
);

create table bitacora (
   id_participante      int4                 not null,
   dia                  int4                 not null,
   mes                  int4                 not null,
   ano                  int4                 not null,
   texto                text                 not null,
   horas_hombre         int4                 not null
);

alter table bitacora
   add constraint pk_bitacora primary key (id_participante, dia, mes, ano);

alter table horas_soporte
   add constraint fk_horas_so_ref_tipo_car foreign key (id_tipo_cargo)
      references tipo_cargo (id_tipo_cargo)
      on delete restrict on update restrict;

alter table horas_soporte
   add constraint fk_horas_so_ref_proyecto foreign key (id_proyecto)
      references proyecto (id_proyecto)
      on delete restrict on update restrict;

alter table participante
   add constraint fk_particip_ref_usuario foreign key (id_usuario)
      references usuario (id_usuario)
      on delete restrict on update restrict;

alter table participante
   add constraint fk_particip_ref_proyecto foreign key (id_proyecto)
      references proyecto (id_proyecto)
      on delete restrict on update restrict;

alter table participante
   add constraint fk_particip_ref_tipo_cargo foreign key (id_tipo_cargo)
      references tipo_cargo (id_tipo_cargo)
      on delete restrict on update restrict;

alter table proyecto
   add constraint fk_proyecto_ref_tipo_pro foreign key (id_tipo_proyecto)
      references tipo_proyecto (id_tipo_proyecto)
      on delete restrict on update restrict;

alter table proyecto
   add constraint fk_proyecto_ref_tipo_est foreign key (id_tipo_estado_proyecto)
      references tipo_estado_proyecto (id_tipo_estado_proyecto)
      on delete restrict on update restrict;

alter table bitacora
   add constraint fk_bitacora_ref_particip foreign key (id_participante)
      references participante (id_participante)
      on delete restrict on update restrict;
