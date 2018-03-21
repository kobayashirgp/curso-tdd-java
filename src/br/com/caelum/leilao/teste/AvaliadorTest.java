package br.com.caelum.leilao.teste;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;
import static org.junit.Assert.assertEquals;

import java.util.List;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario maria;
	private Usuario jose;
	
	@Before
	public void criaAvaliador(){
		this.leiloeiro = new Avaliador();
		joao = new Usuario("Joao");
		jose = new Usuario("Jose");
		maria = new Usuario("Maria");
	}
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		
		
		Leilao leilao =  new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao,250.0)
				.lance(jose,300.0)
				.lance(maria,400.0)
				.constroi();
		
		
		leiloeiro.avalia(leilao);
		
			
		assertThat(leiloeiro.getMaiorLance(),equalTo(400.0));
		assertThat(leiloeiro.getMenorLance(),equalTo(250.0));
				
	}
	
	@Test(expected=RuntimeException.class) 
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado(){
		
			Leilao leilao = new CriadorDeLeilao().para("Playstation 3")
						.constroi();
			leiloeiro.avalia(leilao);	
	}	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
	
		
		Leilao leilao =  new CriadorDeLeilao()
				.para("Playstation 3 Novo")
				.lance(joao,1000.0)
				.constroi();
		
		
		leiloeiro.avalia(leilao);
		
		
		
		assertEquals(1000.0, leiloeiro.getMaiorLance(),0.000001);
	    assertEquals(1000.0, leiloeiro.getMenorLance(),0.000001);
				
	}
	@Test
	public void deveEncontrarOsTresMaioresLances(){
		
		
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
			.lance(joao,100.0)
			.lance(maria,200.0)
			.lance(joao,300.0)
			.lance(maria,400.0)
			.constroi();
		
		
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertEquals(3,	maiores.size());
		assertThat(maiores, hasItems(
				new Lance(maria, 400),
				new Lance(joao, 300),
				new Lance(maria, 200)
		));
	}
}

