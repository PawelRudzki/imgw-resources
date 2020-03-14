package beans;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class CoordinatesBean implements Comparable<CoordinatesBean>{

    @CsvBindByPosition(position = 0)
    private String shortStationId;

    @CsvBindByPosition(position = 1)
    private String longitude1;

    @CsvBindByPosition(position = 2)
    private String longitude2;

    @CsvBindByPosition(position = 3)
    private String latitude1;

    @CsvBindByPosition(position = 4)
    private String latitude2;


    @Override
    public int compareTo(CoordinatesBean o) {
        return this.shortStationId.compareTo(o.getShortStationId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatesBean that = (CoordinatesBean) o;
        return getShortStationId().equals(that.getShortStationId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShortStationId());
    }
}
