package br.jus.trt23.webacesso.enums;

public enum TipoMagistrado
{
	D("Desembargador"), T("Juiz Titular"), S("Juiz Substituto");
	
	private String descricao;

    private TipoMagistrado(String descricao) 
    {
    	this.descricao = descricao;
    }
    
    public String getDescricao()
    {
    	return descricao;
    }  
}