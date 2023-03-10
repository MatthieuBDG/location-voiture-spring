package location.voiture.location.voiture;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class TimeConverter implements Converter<String, Time> {
    @Override
    public Time convert(String s) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            return new Time(format.parse(s).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid time format. Please use HH:mm.");
        }
    }
}

