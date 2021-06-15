package point;

import javax.persistence.*;

import java.util.List;

@Entity
public class Travel {

    @Id
    @GeneratedValue
    private Integer id;
    private List<String> attractions;
    private Integer numberOfSeats;
    private Integer cost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<String> attractions) {
        this.attractions = attractions;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Travel() {
    }

    public Travel(List<String> attractions, Integer numberOfSeats, Integer cost) {
        this.attractions = attractions;
        this.numberOfSeats = numberOfSeats;
        this.cost = cost;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append("wycieczka {")
                .append("ID: " + id + ",")
                .append("Atrakcje: " + attractions + ",")
                .append("Ilość miejsc: " + numberOfSeats + ",")
                .append("Koszt: " + cost + ",")
                .append("}");
        return builder.toString();
    }
}
