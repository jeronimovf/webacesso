package br.jus.trt23.webacesso.entities;

import br.jus.trt23.nucleo.entities.EntidadeGenericaComId;
import br.jus.trt23.nucleo.enums.TipoSimNao;
import br.jus.trt23.webacesso.constants.Constantes;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FUNCIONALIDADE",schema = Constantes.SCHEMA)
@SequenceGenerator(name = "ID", sequenceName = "SEQ_FUNCIONALIDADE", allocationSize = 1)
@Getter
@Setter
@RequiredArgsConstructor
public class Funcionalidade extends EntidadeGenericaComId {

    @JoinColumn(name = "FK_SISTEMA")
    @ManyToOne
    private Sistema sistema;

    @JoinColumn(name = "FK_FUNCIONALIDADE_PAI")
    @ManyToOne
    private Funcionalidade funcionalidade;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_CADASTRO")
    private Date dataCadastro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_EXCLUIDO")
    private Date dataExcluido;

    @Column(name = "USUARIO_CADASTRO")
    private String usuarioCadastro;

    @Column(name = "USUARIO_EXCLUIR")
    private String usuarioExcluir;

    @ManyToMany
    @JoinTable(schema = Constantes.SCHEMA, name = "PERFIL_FUNCIONALIDADE", joinColumns = {
        @JoinColumn(name = "FK_FUNCIONALIDADE")}, inverseJoinColumns = {
        @JoinColumn(name = "FK_PERFIL")})
    public Set<Perfil> listaPerfil;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "funcionalidade", targetEntity = FuncionalidadeUrl.class)
    public Set<FuncionalidadeUrl> listaUrl;

    /**
     * Recursos existentes na funcionalidade, os recursos que o usuário/perfil
     * em questão possuem permissão de acessar estão vinculados ao objeto
     * PERFIL.
     */
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "funcionalidade", targetEntity = Recurso.class)
    public Set<Recurso> listaRecurso;

    @Transient
    private String urlPrincipal;

    public void setListaUrl(Set<FuncionalidadeUrl> listaUrl) {
        this.listaUrl = listaUrl;
    }

    public String getUrls() {
        if (this.getListaUrl() != null) {
            StringBuilder texto = new StringBuilder();
            int tamanho = getListaUrl().size();
            int indice = 1;
            for (FuncionalidadeUrl url : getListaUrl()) {
                if (url.getPrincipalUrl().equals(TipoSimNao.S)) {
                    texto.append("<strong>");
                }
                texto.append(url.getUrl());
                if (url.getPrincipalUrl().equals(TipoSimNao.S)) {
                    texto.append("</strong>");
                }
                if (tamanho > indice) {
                    texto.append("<br/>");
                }
                indice++;
            }
            return texto.toString();
        }
        return "";
    }

    public String getViewIds() {
        if (this.getListaUrl() != null) {
            StringBuilder texto = new StringBuilder();
            int tamanho = this.getListaUrl().size();
            int indice = 1;
            for (FuncionalidadeUrl url : this.getListaUrl()) {
                if (url.getPrincipalUrl().equals(TipoSimNao.S)) {
                    texto.append("<strong>");
                }
                texto.append(url.getViewId());
                if (url.getPrincipalUrl().equals(TipoSimNao.S)) {
                    texto.append("</strong>");
                }
                if (tamanho > indice) {
                    texto.append("<br/>");
                }
                indice++;
            }
            return texto.toString();
        }
        return "";
    }

    @Override
    public String getNomeNatural() {
        return "Funcionalidade";
    }

    @Override
    public String toString() {
        return getDescricao();
    }
    
    
}
