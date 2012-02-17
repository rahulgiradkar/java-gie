create table reserva (
   id_reserva           bigserial            not null,
   id_usuario           int8                 not null,
   fecha_inicio         timestamp            not null,
   fecha_fin            timestamp            not null,
   observacion          varchar(255)         null
);

alter table reserva
   add constraint pk_reserva primary key (id_reserva);

alter table reserva
   add constraint fk_reservado_por_usuario foreign key (id_usuario)
      references usuario (id_usuario)
      on delete restrict on update restrict;

create table reserva_participante (
   id_reserva_participante int8                 not null,
   id_participante      int8                 not null
);

alter table reserva_participante
   add constraint pk_reserva_participante primary key (id_reserva_participante);

alter table reserva_participante
   add constraint fk_reserva_ref_participante foreign key (id_participante)
      references participante (id_participante)
      on delete restrict on update restrict;

alter table reserva_participante
   add constraint fk_reserva_de_participante foreign key (id_reserva_participante)
      references reserva (id_reserva)
      on delete restrict on update restrict;

create table tipo_recurso (
   id_tipo_recurso      int4                 not null,
   nombre               varchar(100)         not null
);

alter table tipo_recurso
   add constraint pk_tipo_recurso primary key (id_tipo_recurso);

create table recurso (
   id_recurso           bigserial            not null,
   id_tipo_recurso      int4                 not null,
   nombre               varchar(100)         not null,
   detalle              varchar(500)         null
);

alter table recurso
   add constraint pk_recurso primary key (id_recurso);

alter table recurso
   add constraint fk_recurso_ref_tipo_rec foreign key (id_tipo_recurso)
      references tipo_recurso (id_tipo_recurso)
      on delete restrict on update restrict;

create table reserva_recurso (
   id_reserva_recurso   int8                 not null,
   id_proyecto          int8                 not null,
   id_recurso           int8                 not null
);

alter table reserva_recurso
   add constraint pk_reserva_recurso primary key (id_reserva_recurso);

alter table reserva_recurso
   add constraint fk_reserva_recurso_ref_proyecto foreign key (id_proyecto)
      references proyecto (id_proyecto)
      on delete restrict on update restrict;

alter table reserva_recurso
   add constraint fk_reserva_ref_recurso foreign key (id_recurso)
      references recurso (id_recurso)
      on delete restrict on update restrict;

alter table reserva_recurso
   add constraint fk_reserva_de_recurso foreign key (id_reserva_recurso)
      references reserva (id_reserva)
      on delete restrict on update restrict;
