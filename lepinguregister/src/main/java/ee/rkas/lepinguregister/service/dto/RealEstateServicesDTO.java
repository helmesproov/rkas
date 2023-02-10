package ee.rkas.lepinguregister.service.dto;

import java.io.Serializable;

public class RealEstateServicesDTO implements Serializable {
    private Long id;

    private String name;

    private Long serviceCount;

    public RealEstateServicesDTO(Long id, String name, Long serviceCount) {
        this.id = id;
        this.name = name;
        this.serviceCount = serviceCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(Long serviceCount) {
        this.serviceCount = serviceCount;
    }
}
