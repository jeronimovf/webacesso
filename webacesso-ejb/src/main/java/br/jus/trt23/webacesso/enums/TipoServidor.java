package br.jus.trt23.webacesso.enums;

public enum TipoServidor
{
	A("Administrativo"), G("Gabinete"), V("Vara");
	
	private String descricao;

    private TipoServidor(String descricao) 
    {
    	this.descricao = descricao;
    }
    
    public String getDescricao()
    {
    	return descricao;
    }  
}