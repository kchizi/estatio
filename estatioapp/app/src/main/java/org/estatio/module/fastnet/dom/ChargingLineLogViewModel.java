package org.estatio.module.fastnet.dom;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ViewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@ViewModel
@Getter @Setter
@AllArgsConstructor
public class ChargingLineLogViewModel {

    public ChargingLineLogViewModel(){};

    @MemberOrder(sequence = "1")
    private String importLog;

    @MemberOrder(sequence = "2")
    private LocalDate applied;

    @MemberOrder(sequence = "3")
    private ImportStatus importStatus;

    @MemberOrder(sequence = "4")
    private String leaseReference;

    @MemberOrder(sequence = "5")
    private String keyToLeaseExternalReference;

    @MemberOrder(sequence = "6")
    private String keyToChargeReference;

    @MemberOrder(sequence = "7")
    private String fromDat;

    @MemberOrder(sequence = "8")
    private String tomDat;

    @MemberOrder(sequence = "9")
    private BigDecimal arsBel;

    @MemberOrder(sequence = "10")
    private LocalDate exportDate;

}
