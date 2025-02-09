
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ParadeRepository;
import domain.Parade;

@Component
public class StringToParadeConverter implements Converter<String, Parade> {

	@Autowired
	ParadeRepository	paradeRepository;


	@Override
	public Parade convert(final String text) {
		Parade result;
		System.out.println("text del converter");
		System.out.println(text);
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.paradeRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToParadeConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
