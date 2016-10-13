create user webacesso identified by "webacesso";
grant connect to webacesso;
grant unlimited tablespace to webacesso;
grant create view to webacesso;
grant create table to webacesso;
grant create sequence to webacesso;

grant select, references on srh2.tab_si_func to webacesso with grant option;
grant select, references on srh2.servidor to webacesso with grant option;
grant select, references on srh2.lotacao to webacesso with grant option;
grant select, references on srh2.unidade_tse to webacesso with grant option;
grant select, references on srh2.qfc_ocup_com to webacesso with grant option;
grant select, references on srh2.qfc_vagas_com to webacesso with grant option;
grant select, references on srh2.provimento to webacesso with grant option;
grant select, references on srh2.vagas_cat_func to webacesso with grant option;
grant select, references on srh2.atos to webacesso with grant option;
grant select, references on srh2.cargo to webacesso with grant option;
grant select, references on srh2.vagas_transf to webacesso with grant option;

create role rl_webacesso;
grant select, insert, update on webacesso.delegar_gestor to rl_webacesso;
grant select on webacesso.seq_delegar_gestor to rl_webacesso;
grant select on webacesso.funcionalidade to rl_webacesso;
grant select on webacesso.funcionalidade_url to rl_webacesso;
grant select on webacesso.perfil to rl_webacesso;
grant select on webacesso.perfil_funcionalidade to rl_webacesso;
grant select on webacesso.perfil_recurso to rl_webacesso;
grant select on webacesso.permissao to rl_webacesso;
grant select on webacesso.recurso to rl_webacesso;
grant select on webacesso.sistema to rl_webacesso;
grant select on webacesso.tipo_usuario to rl_webacesso;
grant select on webacesso.usuario_externo to rl_webacesso;
grant select on webacesso.usuario_sistema to rl_webacesso;
grant select on webacesso.unidade_sem_gestor to rl_webacesso;
grant select on webacesso.vw_acesso to rl_webacesso;
grant select on webacesso.vw_unidade to rl_webacesso;
grant select on webacesso.vw_usuario to rl_webacesso;

create sequence webacesso.seq_usuario_externo minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;
create sequence webacesso.seq_sistema minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;
create sequence webacesso.seq_funcionalidade minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;
create sequence webacesso.seq_perfil minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;
create sequence webacesso.seq_perfil_funcionalidade minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;
create sequence webacesso.seq_permissao minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;
create sequence webacesso.seq_recurso minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;
create sequence webacesso.seq_funcionalidade_url minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;
create sequence webacesso.seq_unidade_sem_gestor minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;
create sequence webacesso.seq_delegar_gestor minvalue 1 maxvalue 999999999999 start with 1 increment by 1 nocache;


create table webacesso.tipo_usuario
(
  id        NUMBER not null,
  descricao VARCHAR2(100) not null
);
alter table webacesso.tipo_usuario add constraint pk_tipo_usuario primary key (id);


create table webacesso.usuario_sistema
(
  id        NUMBER not null,
  descricao VARCHAR2(100) not null
);
alter table webacesso.usuario_sistema add constraint pk_usuario_sistema primary key (id);


create table webacesso.usuario_externo
(
  id               number not null,
  fk_tipo_usuario  number not null,
  login            varchar2(70) not null,
  nome             varchar2(60) not null,
  cpf              varchar2(11) not null,
  email            varchar2(50),
  matricula        varchar2(8),
  data_nascimento  date not null,
  data_cadastro    timestamp not null,
  data_excluido    timestamp,
  usuario_cadastro varchar2(70) not null,
  usuario_excluir varchar2(70)
);
comment on column webacesso.usuario_externo.login is 'Login LDAP';
comment on column webacesso.usuario_externo.data_cadastro is 'Data em que registro foi cadastrado';
comment on column webacesso.usuario_externo.data_excluido is 'Data em que registro foi excluído';
comment on column webacesso.usuario_externo.usuario_cadastro is 'Login LDAP do usuário que realizou o cadastro';
comment on column webacesso.usuario_externo.usuario_excluir is 'Login LDAP do usuário que realizou a exclusão'; 
alter table webacesso.usuario_externo add constraint PK_USUARIO primary key (ID);
alter table webacesso.usuario_externo add constraint FK_TIPO_USUARIO foreign key (FK_TIPO_USUARIO) references webacesso.tipo_usuario (ID);


create table webacesso.sistema
(
  id               number not null,
  descricao        varchar2(100) not null,
  sigla            varchar2(10) not null,
  url              varchar2(200),
  data_cadastro    timestamp not null,
  data_excluido    timestamp,
  usuario_cadastro varchar2(70) not null,
  usuario_excluir varchar2(70)
);
comment on column webacesso.sistema.data_cadastro is 'Data em que registro foi cadastrado';
comment on column webacesso.sistema.data_excluido is 'Data em que registro foi excluído';
comment on column webacesso.sistema.usuario_cadastro is 'Login LDAP do usuário que realizou o cadastro';
comment on column webacesso.sistema.usuario_excluir is 'Login LDAP do usuário que realizou a exclusão'; 
alter table webacesso.sistema add constraint pk_sistema primary key (ID);


create table webacesso.funcionalidade
(
  id                     number not null,
  fk_sistema  			 number not null,
  fk_funcionalidade_pai  number,
  descricao              varchar2(100) not null,
  data_cadastro   	     timestamp not null,
  data_excluido  	     timestamp,
  usuario_cadastro	     varchar2(70) not null,
  usuario_excluir 		 varchar2(70)
);
comment on column webacesso.funcionalidade.data_cadastro is 'Data em que registro foi cadastrado';
comment on column webacesso.funcionalidade.data_excluido is 'Data em que registro foi excluído';
comment on column webacesso.funcionalidade.usuario_cadastro is 'Login LDAP do usuário que realizou o cadastro';
comment on column webacesso.funcionalidade.usuario_excluir is 'Login LDAP do usuário que realizou a exclusão'; 
alter table webacesso.funcionalidade add constraint pk_funcionalidade primary key (id);
alter table webacesso.funcionalidade add constraint fk_sistema foreign key (fk_sistema) references webacesso.sistema (ID);
alter table webacesso.funcionalidade add constraint fk_funcionalidade_pai foreign key (fk_funcionalidade_pai) references webacesso.funcionalidade (id);


create table webacesso.funcionalidade_url
(
  id        		NUMBER not null,
  fk_funcionalidade NUMBER not null,
  principal_url		varchar2(1) not null,
  view_id			varchar2(400) not null,
  url			 	varchar2(200) not null
);
alter table webacesso.funcionalidade_url add constraint pk_funcionalidade_url primary key (id);
alter table webacesso.funcionalidade_url add constraint fk_funcionalidade_url foreign key (fk_funcionalidade) references webacesso.funcionalidade (id);
alter table webacesso.funcionalidade_url add constraint uk_funcionalidade_url unique (fk_funcionalidade, view_id);
comment on column webacesso.funcionalidade_url.principal_url is 'S - Sim; N - Não';
comment on column webacesso.funcionalidade_url.view_id is 'Url completa da funcionalidade (utilizada para verificar permissão)';


create table webacesso.recurso
(
  id        		NUMBER not null,
  fk_funcionalidade NUMBER not null,
  descricao 		VARCHAR2(50) not null
);
alter table webacesso.recurso add constraint pk_recurso primary key (id);
alter table webacesso.recurso add constraint fk_func_recurso foreign key (fk_funcionalidade) references webacesso.funcionalidade (id);
alter table webacesso.recurso add constraint uk_recurso unique (fk_funcionalidade, descricao);

create table webacesso.perfil
(
  id               number not null,
  fk_sistema  	   number not null,
  descricao        varchar2(100) not null,
  sigla            varchar2(50) not null,
  data_cadastro    timestamp not null,
  data_excluido    timestamp,
  usuario_cadastro varchar2(70) not null,
  usuario_excluir varchar2(70)
);
comment on column webacesso.perfil.data_cadastro is 'Data em que registro foi cadastrado';
comment on column webacesso.perfil.data_excluido is 'Data em que registro foi excluído';
comment on column webacesso.perfil.usuario_cadastro is 'Login LDAP do usuário que realizou o cadastro';
comment on column webacesso.perfil.usuario_excluir is 'Login LDAP do usuário que realizou a exclusão'; 
alter table webacesso.perfil add constraint pk_perfil primary key (ID);
alter table webacesso.perfil add constraint fk_sistema_1 foreign key (fk_sistema) references webacesso.sistema (ID);


create table webacesso.perfil_funcionalidade
(
  fk_perfil  			 number not null,
  fk_funcionalidade      number not null
);
alter table webacesso.perfil_funcionalidade add constraint pk_perfil_funcionalidade primary key (fk_perfil, fk_funcionalidade);
alter table webacesso.perfil_funcionalidade add constraint fk_perf_func foreign key (fk_perfil) references webacesso.perfil (id);
alter table webacesso.perfil_funcionalidade add constraint fk_func_perf foreign key (fk_funcionalidade) references webacesso.funcionalidade (id);


create table webacesso.perfil_recurso
(
  fk_perfil  			 number not null,
  fk_recurso      		 number not null
);
alter table webacesso.perfil_recurso add constraint pk_perfil_recurso primary key (fk_perfil, fk_recurso);
alter table webacesso.perfil_recurso add constraint fk_perf_rec_perfil foreign key (fk_perfil) references webacesso.perfil (id);
alter table webacesso.perfil_recurso add constraint fk_perf_rec_recurso foreign key (fk_recurso) references webacesso.recurso (id);


create table webacesso.permissao
(
  id					 number not null,
  login                  varchar2(70) not null,  
  fk_perfil  			 number not null,
  fk_unidade             number,
  data_cadastro    		 timestamp not null,
  data_excluido    		 timestamp,
  usuario_cadastro 		 varchar2(70) not null,
  usuario_excluir 		 varchar2(70)
);
comment on column webacesso.permissao.data_cadastro is 'Data em que registro foi cadastrado';
comment on column webacesso.permissao.data_excluido is 'Data em que registro foi excluído';
comment on column webacesso.permissao.usuario_cadastro is 'Login LDAP do usuário que realizou o cadastro';
comment on column webacesso.permissao.usuario_excluir is 'Login LDAP do usuário que realizou a exclusão'; 
alter table webacesso.permissao add constraint pk_permissao primary key (id);
alter table webacesso.permissao add constraint fk_perfil_usuario foreign key (fk_perfil) references webacesso.perfil (id);


create table webacesso.unidade_sem_gestor
(
  id               number not null,
  fk_unidade	   number not null,
  login			   varchar2(70) not null,
  data_cadastro    timestamp not null,
  data_excluido    timestamp,
  usuario_cadastro varchar2(70) not null,
  usuario_excluir  varchar2(70)
);
alter table webacesso.unidade_sem_gestor add constraint pk_unidade_sem_gestor primary key (id);
comment on table webacesso.unidade_sem_gestor is 'Indica qual função comissionada representa o gestor da unidade na qual ela está lotada.';


create table webacesso.delegar_gestor
(
  id					 number not null,
  fk_sistema             number not null,  
  usuario_gestor		 varchar2(70) not null,
  usuario_delegado		 varchar2(70) not null,  
  data_cadastro    		 date not null,
  data_excluido    		 date,
  usuario_cadastro 		 varchar2(70) not null,
  usuario_excluir 		 varchar2(70)
);
alter table webacesso.delegar_gestor add constraint pk_delegar_gestor primary key (id);
alter table webacesso.delegar_gestor add constraint fk_sistema_gestor foreign key (fk_sistema) references webacesso.sistema (id);


create or replace view webacesso.vw_acesso as
select pe.id as id, pe.login as login, pr.fk_sistema as sistema, pe.fk_perfil as perfil, 
       nvl(pe.fk_unidade, lot.cod_unid_tse) as unidade
from webacesso.permissao pe
left join webacesso.vw_unidade un on pe.fk_unidade = un.id_unidade
inner join webacesso.vw_usuario us on pe.login = us.login
inner join webacesso.perfil pr on pe.fk_perfil = pr.id
left join srh2.servidor ser on pe.login = ser.login
left join srh2.lotacao lot on lot.mat_servidor = ser.mat_servidor and sysdate between lot.dt_ini_lotacao and nvl(lot.dt_fim_lotacao, sysdate)
where 1 = 1
and sysdate between pe.data_cadastro and nvl(pe.data_excluido, sysdate)
union
select pe.id as id, pe.login, pr.fk_sistema, pe.fk_perfil, lot.cod_unid_tse
from webacesso.permissao pe
inner join webacesso.perfil pr on pe.fk_perfil = pr.id
inner join srh2.servidor ser on pe.login = ser.login
inner join srh2.lotacao lot on lot.mat_servidor = ser.mat_servidor and sysdate between lot.dt_ini_lotacao and nvl(lot.dt_fim_lotacao, sysdate)
where 1 = 1
and sysdate between pe.data_cadastro and nvl(pe.data_excluido, sysdate)
and pe.fk_unidade is null;


create or replace view webacesso.vw_usuario as
select s.login as login, s.mat_servidor as matricula, s.nom as nome, s.num_cpf as cpf, s.mat_serv_completa as matricula_trt, s.e_mail as email, s.e_mail_externo as email_externo, s.dt_nasc as data_nascimento,
       case when vcom.unid_atual_com is not null and vcom.unid_atual_com not in (824,826) then /*dá prioridade sobre a unidade da fc/cj, exceto na reserva técnica*/
         vcom.unid_atual_com
       else
         u.cd
       end as id_unidade,
       ocup.cod_comissionado, ocup.nome_com, ocup.num_vaga_com, ocup.titular_com, 'I' as tipo_usuario,
       decode(nvl(vag_transf.seq_cargo, vag.seq_cargo), 93, 'D', 94, 'T', 95, 'S') as tipo_magistrado,
       case
         when nvl(vag_transf.seq_cargo, vag.seq_cargo) is null and u.sigla_unid_tse like 'GDF%' then 'G'
         when nvl(vag_transf.seq_cargo, vag.seq_cargo) is null and (u.sigla_unid_tse like '_VT%' or u.sigla_unid_tse like 'VT%') then 'V'
         when nvl(vag_transf.seq_cargo, vag.seq_cargo) is null then 'A'
       end as tipo_servidor,
       case
         when ocup.titular_com = 1 and ocup.cod_comissionado like 'CJ%' then 'S'
         when usg.login is not null then 'S'
       end gestor,
       sf.ds as situacao_funcional,
       s.dt_deslig as data_desligamento            
from srh2.servidor s
left join srh2.tab_si_func sf on s.cd_si_func = sf.cd
left join srh2.lotacao l on s.mat_servidor = l.mat_servidor and trunc(sysdate) between l.dt_ini_lotacao and nvl(l.dt_fim_lotacao, sysdate)
left join srh2.unidade_tse u on l.cod_unid_tse = u.cd
left join srh2.qfc_ocup_com ocup on s.mat_servidor = ocup.mat_servidor and trunc(sysdate) between ocup.dt_ingresso and nvl(ocup.dt_dispensa, sysdate)
left join srh2.qfc_vagas_com vcom on ocup.cod_comissionado = vcom.cod_comissionado and ocup.nome_com = vcom.nome_com and ocup.num_vaga_com = vcom.num_vaga_com
left join srh2.provimento p on s.mat_servidor = p.mat_servidor and trunc(sysdate) between p.dt_posse and nvl(p.dt_vacancia, sysdate)
left join srh2.vagas_cat_func vag on vag.seq_cargo = p.cd and p.nr = vag.nr and vag.seq_cargo in (93, 94, 95, 96, 97)
left join srh2.atos ato on nvl(vag.cd_criacao, vag.cd_transf) = ato.cd
left join srh2.cargo cargo on vag.seq_cargo = cargo.seq_cargo
left join srh2.vagas_transf transf on vag.seq_cargo = transf.seq_cargo and vag.nr = transf.nr
left join srh2.vagas_cat_func vag_transf on transf.seq_cargo_dest = vag_transf.seq_cargo and transf.nr_cat_func_dest = vag_transf.nr
left join srh2.cargo novo_cargo on vag_transf.seq_cargo = novo_cargo.seq_cargo
left join webacesso.unidade_sem_gestor usg on s.login = usg.login and sysdate between usg.data_cadastro and nvl(usg.data_excluido, sysdate)
union
select ue.login as login, ue.matricula as matricula, ue.nome as nome, ue.cpf as cpf, null as matricula_trt, ue.email as email, null, ue.data_nascimento as data_nascimento, null as id_unidade,
       null as cod_comissionado, null as nome_com, null as num_vaga_com, null as titular_com, 'E' as tipo_usuario, null, null, null, null, null
from webacesso.usuario_externo ue
where sysdate between ue.data_cadastro and nvl(ue.data_excluido, sysdate)
union
select us.descricao as login, null as matricula, us.descricao as nome, null as cpf, null as matricula_trt, null as email, null as email_externo, null as data_nascimento,
       null as id_unidade, null as cod_comissionado, null as nome_com, null as num_vaga_com, null as titular_com, 'S' as tipo_sistema, null, null, null, null, null
from webacesso.usuario_sistema us
order by 3;
comment on column WEBACESSO.VW_USUARIO.TIPO_USUARIO is 'E - Externo; I - Interno; S - Sistema;';
comment on column WEBACESSO.VW_USUARIO.TIPO_MAGISTRADO is 'D - Desembargador; T - Juiz Titular; S - Juiz Substituto;';
comment on column WEBACESSO.VW_USUARIO.TIPO_SERVIDOR is 'G - Gabinete; V - Vara; A - Administrativo;';
comment on column WEBACESSO.VW_USUARIO.GESTOR is 'S - Sim; N - Não;';


create or replace view webacesso.vw_unidade as
select u.cd as id_unidade, u.cod_unid_super as id_unidade_superior, u.sigla_unid_tse as sigla, u.ds as descricao, u.e_mail as email
from srh2.unidade_tse u
where u.sit_unid not like 'E%'
order by u.ds;