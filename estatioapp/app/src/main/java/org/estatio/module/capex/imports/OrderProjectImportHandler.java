package org.estatio.module.capex.imports;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.factory.FactoryService;

import org.isisaddons.module.excel.dom.ExcelFixture2;
import org.isisaddons.module.excel.dom.ExcelMetaDataEnabled;
import org.isisaddons.module.excel.dom.FixtureAwareRowHandler;

import lombok.Getter;
import lombok.Setter;

public class OrderProjectImportHandler implements FixtureAwareRowHandler<OrderProjectImportHandler>, ExcelMetaDataEnabled {

    @Getter @Setter @Nullable
    private String excelSheetName;

    @Getter @Setter @Nullable
    private Integer excelRowNumber;

    @Getter @Setter @Nullable
    private Integer numero;

    @Getter @Setter @Nullable
    private String centro;

    @Getter @Setter @Nullable
    private Integer progressivoCentro;

    @Getter @Setter @Nullable
    private Integer commessa;

    @Getter @Setter @Nullable
    private Integer workType;

    @Getter @Setter @Nullable
    private Integer integrazione;

    @Getter @Setter @Nullable
    private LocalDate data;

    @Getter @Setter @Nullable
    private String oggetto;

    @Getter @Setter @Nullable
    private String fornitore;

    @Getter @Setter @Nullable
    private String codiceFornitore;

    @Getter @Setter @Nullable
    private BigDecimal importoNettoIVA;

    @Getter @Setter @Nullable
    private BigDecimal cassaProfess;

    @Getter @Setter @Nullable
    private BigDecimal importoTotale;

    @Getter @Setter @Nullable
    private BigDecimal iva;

    @Getter @Setter @Nullable
    private BigDecimal totaleConIVA;

    @Getter @Setter @Nullable
    private String autorizzato;

    @Getter @Setter @Nullable
    private String note;


    /**
     * To allow for usage within fixture scripts also.
     */
    @Setter
    private FixtureScript.ExecutionContext executionContext;

    /**
     * To allow for usage within fixture scripts also.
     */
    @Setter
    private ExcelFixture2 excelFixture2;

    public OrderProjectLine handle(final OrderProjectImportHandler previousRow){
        OrderProjectLine newLine =  new OrderProjectLine(getExcelSheetName(), getExcelRowNumber(),
                getNumero().toString(), getCentro(), getProgressivoCentro(),
                getCommessa(), getWorkType(), getIntegrazione(),
                getData(), getOggetto(),
                clean(getFornitore()),
                getCodiceFornitore(),
                getImportoNettoIVA()!=null ? getImportoNettoIVA().setScale(2,  RoundingMode.HALF_UP) : null,
                getCassaProfess()!=null ? getCassaProfess().setScale(2, RoundingMode.HALF_UP) : null,
                getImportoTotale()!=null ? getImportoTotale().setScale(2, RoundingMode.HALF_UP) : null,
                getIva()!=null ? getIva().setScale(2, RoundingMode.HALF_UP) : null,
                getTotaleConIVA()!=null ? getTotaleConIVA().setScale(2, RoundingMode.HALF_UP) : null,
                getAutorizzato(),
                getNote());
        OrderProjectLine._apply applyMixin = factoryService.mixin(OrderProjectLine._apply.class, newLine);
        applyMixin.act();
        return newLine;
    }

    @Override
    public void handleRow(final OrderProjectImportHandler previousRow) {

            if(executionContext != null && excelFixture2 != null) {
                executionContext.addResult(excelFixture2,this.handle(previousRow));
            }

    }

    private String clean(final String input){
        if (input==null){
            return null;
        }
        String result = input.trim();
        return result.trim();
    }

    @Inject FactoryService factoryService;

}

