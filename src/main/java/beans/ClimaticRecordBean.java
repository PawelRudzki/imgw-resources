package beans;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Objects;

@Getter
@Setter
@ToString

public class ClimaticRecordBean implements Comparable<ClimaticRecordBean> {

    @CsvBindByPosition(position = 0)
    private String id;

    @CsvBindByPosition(position = 1)
    private String name;


    @CsvBindByPosition(position = 2)
    private String year;

    @CsvBindByPosition(position = 3)
    private String month;

    @CsvBindByPosition(position = 4)
    private String day;

    @CsvBindByPosition(position = 5)
    private String tmpMax;

    @CsvBindByPosition(position = 6)
    private String tmpMaxStatus;

    @CsvBindByPosition(position = 7)
    private String tmpMin;

    @CsvBindByPosition(position = 8)
    private String tmpMinStatus;

    @CsvBindByPosition(position = 9)
    private String tmpAvg;

    @CsvBindByPosition(position = 10)
    private String tmpAvgStatus;

    @CsvBindByPosition(position = 11)
    private String tmpOfGroundMin;

    @CsvBindByPosition(position = 12)
    private String tmpOfGroundMinStatus;

    @CsvBindByPosition(position = 13)
    private String totalPrecipitation;

    @CsvBindByPosition(position = 14)
    private String totalPrecipitationStatus;

    @CsvBindByPosition(position = 15)
    private String kindOfPrecipitation;

    @CsvBindByPosition(position = 16)
    private String snowLayerHeight;

    @CsvBindByPosition(position = 17)
    private String snowLayerHeightStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClimaticRecordBean bean = (ClimaticRecordBean) o;
        return getId().equals(bean.getId()) &&
                getYear().equals(bean.getYear()) &&
                getMonth().equals(bean.getMonth()) &&
                getDay().equals(bean.getDay());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getYear(), getMonth(), getDay());
    }

    @Override
    public int compareTo(ClimaticRecordBean o) {
        String thisDate = this.getId() + this.getDay() + this.getMonth() + this.getYear();
        String oDate = o.getId() + o.getDay() + o.getMonth() + o.getYear();
        return thisDate.compareTo(oDate);
    }
//    Kod stacji                              9
//    Nazwa stacji                           30
//    Rok                                     4
//    Miesi¹c                                 2
//    Dzieñ                                   2
//    Maksymalna temperatura dobowa [°C]      6/1
//    Status pomiaru TMAX                     1
//    Minimalna temperatura dobowa [°C]       6/1
//    Status pomiaru TMIN                     1
//    Œrednia temperatura dobowa [°C]         8/1
//    Status pomiaru STD                      1
//    Temperatura minimalna przy gruncie [°C] 6/1
//    Status pomiaru TMNG                     1
//    Suma dobowa opadów [mm]                 8/1
//    Status pomiaru SMDB                     1
//    Rodzaj opadu  [S/W/ ]                   1
//    Wysokoœæ pokrywy œnie¿nej [cm]          5
//    Status pomiaru PKSN                     1
//
//    Status "8" brak pomiaru
//    Status "9" brak zjawiska
}
