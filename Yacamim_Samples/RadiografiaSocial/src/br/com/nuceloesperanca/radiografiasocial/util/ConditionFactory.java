package br.com.nuceloesperanca.radiografiasocial.util;

import android.view.View;
import br.org.yacamim.ui.components.Condition;
import br.org.yacamim.ui.components.RowCondition;
import br.org.yacamim.ui.components.RowConfig;

/**
 * Fábrica de condições.
 * 
 * @author manny.
 * Criado em Feb 12, 2013 3:09:07 PM
 * @version 1.0
 * @since 1.0
 */
public final class ConditionFactory {

	/**
	 * Construtor da classe.
	 */
	private ConditionFactory() {
		super();
	}

	/**
	 * @return
	 */
	public static Condition getConditionPaciente() {
		return new Condition() {
			@Override
			public boolean checkToVisibility(final Object dado) {
				return true;
			}

			@Override
			public void handle(final Object dado, final View view) {
				// Não implementado neste exemplo
			}
		};
	}

	/** 
	 * @return
	 */
	public static RowCondition getSimpleRowCondition() {
		return new RowCondition() {
			@Override
			public RowConfig selectRowConfig(Object _data, int _position, RowConfig[] _rowConfigs) {
				return _rowConfigs[0];
			}
		};
	}
}