package br.com.util;

import java.lang.reflect.Field;

public class UtilReflection {

	private static final String PATTERN = "\\.";

	public static Object getValor(final String campo, final Object entidade) {

		try {

			final Field field = entidade.getClass().getDeclaredField(campo);

			field.setAccessible(true);

			return field.get(entidade);

		} catch (final Exception e) {
			try {
				Field field = entidade.getClass().getSuperclass().getDeclaredField(campo);
				field.setAccessible(true);

				return field.get(entidade);
			} catch (Exception e1) {
			}
		}
		return null;

	}

	public static void setValor(final String campo, final Object entidade, final Object valor) {

		try {

			final Field field = entidade.getClass().getDeclaredField(campo);

			field.setAccessible(true);

			field.set(entidade, valor);

		} catch (final Exception e) {
			try {
				Field field = entidade.getClass().getSuperclass().getDeclaredField(campo);
				field.setAccessible(true);

				field.set(entidade, valor);
			} catch (Exception e1) {
			}
		}

	}

	public static boolean isNull(final Object entidade, final String campo) {

		final String[] dados = campo.split(PATTERN);

		Object pesquisador = entidade;

		for (final String valor : dados) {

			final Object item = UtilReflection.getValor(valor, pesquisador);

			if (item == null)
				return true;

			pesquisador = item;
		}
		return false;
	}

	public static Object getNullOrValue(final Object entidade, final String campo) {

		final String[] dados = campo.split(PATTERN);

		Object pesquisador = entidade;

		for (final String valor : dados) {

			final Object item = UtilReflection.getValor(valor, pesquisador);

			if (item == null)
				return null;

			pesquisador = item;

		}

		return pesquisador;
	}

	public static String getNullOrValueString(final Object entidade, final String campo) {

		final String[] dados = campo.split(PATTERN);

		Object pesquisador = entidade;

		for (final String valor : dados) {

			final Object item = UtilReflection.getValor(valor, pesquisador);

			if (item == null)
				return null;

			pesquisador = item;

		}

		return pesquisador.toString();
	}

}
