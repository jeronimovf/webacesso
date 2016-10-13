package br.jus.trt23.webacesso.enums;

public enum TipoUsuario
{
	E("Externo"), I("Interno"), S("Sistema");
	
	private String descricao;

    private TipoUsuario(String descricao) 
    {
    	this.descricao = descricao;
    }
    
    public String getDescricao()
    {
    	return descricao;
    }     
}