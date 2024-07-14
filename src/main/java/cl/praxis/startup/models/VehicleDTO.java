package cl.praxis.startup.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDTO {
    private int id;
    private String name;
    private String url;
    private int userId;

    public VehicleDTO(String name, String url, int userId) {
        this.name = name;
        this.url = url;
        this.userId = userId;
    }
}
