package beans;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationBean extends CsvBean {

    @CsvBindByPosition(position = 0)
    private String id;

    @CsvBindByPosition(position = 1)
    private String name;

}
