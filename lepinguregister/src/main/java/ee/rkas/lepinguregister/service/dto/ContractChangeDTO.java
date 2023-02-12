package ee.rkas.lepinguregister.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class ContractChangeDTO implements Serializable {
    private Long id;
    private Long serviceId;
    private Long realEstateId;
    private Long pendingServiceId;
    private Long actId;
    private String contractName;
    private String realEstateName;
    private String serviceName;
    private BigDecimal price;
    private Instant validFrom;
    private Instant validTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getRealEstateName() {
        return realEstateName;
    }

    public void setRealEstateName(String realEstateName) {
        this.realEstateName = realEstateName;
    }
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public Long getRealEstateId() {
        return realEstateId;
    }

    public void setRealEstateId(Long realEstateId) {
        this.realEstateId = realEstateId;
    }

    public Long getPendingServiceId() {
        return pendingServiceId;
    }

    public void setPendingServiceId(Long pendingServiceId) {
        this.pendingServiceId = pendingServiceId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public ContractChangeDTO(Long id, Long serviceId, Long realEstateId, Long pendingServiceId, Long actId, String contractName, String realEstateName, String serviceName, BigDecimal price, Instant validFrom, Instant validTo) {
        this.id = id;
        this.contractName = contractName;
        this.realEstateName = realEstateName;
        this.serviceName = serviceName;
        this.price = price;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.serviceId = serviceId;
        this.pendingServiceId = pendingServiceId;
        this.realEstateId = realEstateId;
        this.actId = actId;
    }
}
