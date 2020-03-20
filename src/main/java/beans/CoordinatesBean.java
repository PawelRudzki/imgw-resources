package beans;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesBean implements Comparable<CoordinatesBean>{

    @CsvBindByPosition(position = 0)
    private String shortStationId;

    @CsvBindByPosition(position = 1)
    private int longitudeDeg;

    @CsvBindByPosition(position = 2)
    private int longitudeMin;

    @CsvBindByPosition(position = 3)
    private int latitudeDeg;

    @CsvBindByPosition(position = 4)
    private int latitudeMin;


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
