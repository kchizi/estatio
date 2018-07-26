package org.estatio.module.capex.imports;

import java.math.BigDecimal;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import org.isisaddons.module.excel.dom.ExcelFixture2;
import org.isisaddons.module.excel.dom.ExcelMetaDataEnabled;
import org.isisaddons.module.excel.dom.FixtureAwareRowHandler;

import org.estatio.module.party.imports.OrganisationImport;

import lombok.Getter;
import lombok.Setter;

public class OrderProjectImportAdapter implements FixtureAwareRowHandler<OrderProjectImportAdapter>, ExcelMetaDataEnabled {

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

    public OrderProjectImportAdapter handle(final OrderProjectImportAdapter previousRow){
        if (getCodiceFornitore()!=null) importSeller();
        if (getNumero()!=null && getCentro()!=null) importOrder();
        return this;
    }

    private void importOrder() {
        OrderImport newLine = new OrderImport(
                getCentro(),
                "CAPEX",
                getNumero().toString(),
                null,
                getData(),
                getData(),
                getCodiceFornitore(),
                null,
                "/ITA",
                "APPROVED",
                getAutorizzato(),
                getData()
        );
        serviceRegistry2.injectServicesInto(newLine);
        newLine.importData(null);
    }

    private void importSeller() {
        OrganisationImport organisationImport = new OrganisationImport();
        serviceRegistry2.injectServicesInto(organisationImport);
        organisationImport.setName(clean(getFornitore()));
        organisationImport.setAtPath("/ITA");
        organisationImport.setReference(getCodiceFornitore());
        organisationImport.importData(null);
    }

    private String clean(final String input){
        if (input==null){
            return null;
        }
        String result = input.trim();
        return result.trim();
    }

    @Override
    public void handleRow(final OrderProjectImportAdapter previousRow) {
            if(executionContext != null && excelFixture2 != null) {
                executionContext.addResult(excelFixture2,this.handle(previousRow));
            }

    }

    @Inject ServiceRegistry2 serviceRegistry2;

}

