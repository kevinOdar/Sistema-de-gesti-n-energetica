package testGenesis;

import java.util.ArrayList;
import java.util.List;

import models.dao.hibernate.DaoMySQL;
import models.entities.dominio.CategoriaConsumo;

public class CreadorCategorias {

	private DaoMySQL<CategoriaConsumo> persistidor;
	private List<CategoriaConsumo> categoriasSistema;
	private CategoriaConsumo categoria1;
	private CategoriaConsumo categoria2;
	private CategoriaConsumo categoria3;
	private CategoriaConsumo categoria4;
	private CategoriaConsumo categoria5;
	private CategoriaConsumo categoria6;
	private CategoriaConsumo categoria7;
	private CategoriaConsumo categoria8;
	private CategoriaConsumo categoria9;
	
	public void init () {
		persistidor = new DaoMySQL<CategoriaConsumo>();
		persistidor.init();
		persistidor.setTipo(CategoriaConsumo.class);
		
		//cat1
		categoria1 = new CategoriaConsumo();
		categoria1.setNombre("R1");
		categoria1.setCargoFijo(18.76);
		categoria1.setCargoVariable(0.644);
		categoria1.setConsumoMax(150);
		categoria1.setConsumoMin(0);

		//cat 2
		categoria2 = new CategoriaConsumo();
		categoria2.setNombre("R2");
		categoria2.setCargoFijo(35.32);
		categoria2.setCargoVariable(0.644);
		categoria2.setConsumoMax(325);
		categoria2.setConsumoMin(150);
		
		//cat3
		categoria3 = new CategoriaConsumo();
		categoria3.setNombre("R3");
		categoria3.setCargoFijo(60.71);
		categoria3.setCargoVariable(0.681);
		categoria3.setConsumoMax(400);
		categoria3.setConsumoMin(325);

		//cat4
		categoria4 = new CategoriaConsumo();
		categoria4.setNombre("R4");
		categoria4.setCargoFijo(71.74);
		categoria4.setCargoVariable(0.738);
		categoria4.setConsumoMax(450);
		categoria4.setConsumoMin(400);
		
		//cat5
		categoria5 = new CategoriaConsumo();
		categoria5.setNombre("R5");
		categoria5.setCargoFijo(110.38);
		categoria5.setCargoVariable(0.794);
		categoria5.setConsumoMax(500);
		categoria5.setConsumoMin(450);

		//cat6
		categoria6 = new CategoriaConsumo();
		categoria6.setNombre("R6");
		categoria6.setCargoFijo(220.75);
		categoria6.setCargoVariable(0.832);
		categoria6.setConsumoMax(600);
		categoria6.setConsumoMin(500);
		
		//cat7
		categoria7 = new CategoriaConsumo();
		categoria7.setNombre("R7");
		categoria7.setCargoFijo(443.59);
		categoria7.setCargoVariable(0.851);
		categoria7.setConsumoMax(700);
		categoria7.setConsumoMin(600);

		//cat8
		categoria8 = new CategoriaConsumo();
		categoria8.setNombre("R8");
		categoria8.setCargoFijo(545.96);
		categoria8.setCargoVariable(0.851);
		categoria8.setConsumoMax(1400);
		categoria8.setConsumoMin(700);
		
		//cat9
		categoria9 = new CategoriaConsumo();
		categoria9.setNombre("R9");
		categoria9.setCargoFijo(887.19);
		categoria9.setCargoVariable(0.851);
		categoria9.setConsumoMax(1400);
		categoria9.setConsumoMin(1400);
	
		categoriasSistema = new ArrayList<>();
		categoriasSistema.add(categoria1);
		categoriasSistema.add(categoria2);
		categoriasSistema.add(categoria3);
		categoriasSistema.add(categoria4);
		categoriasSistema.add(categoria5);
		categoriasSistema.add(categoria6);
		categoriasSistema.add(categoria7);
		categoriasSistema.add(categoria8);
		categoriasSistema.add(categoria9);

		persistidor.agregarTodos(categoriasSistema);
	}


}
