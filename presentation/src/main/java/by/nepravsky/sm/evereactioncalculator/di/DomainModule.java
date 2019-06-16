package by.nepravsky.sm.evereactioncalculator.di;

import by.nepravsky.sm.domain.usecase.downlevel.GetFormula;
import by.nepravsky.sm.domain.usecase.downlevel.GetMaterialMap;
import by.nepravsky.sm.domain.usecase.downlevel.GetMaterialPriceMap;
import by.nepravsky.sm.domain.usecase.downlevel.caseinterface.FormulaCase;
import by.nepravsky.sm.domain.usecase.downlevel.caseinterface.MaterialMapCase;
import by.nepravsky.sm.domain.usecase.downlevel.caseinterface.MaterialPriceMapCase;
import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

    @Provides
    public FormulaCase provideFormulaCase(GetFormula getFormula){
        return getFormula;
    }

    @Provides
    public MaterialMapCase provideMaterialMapCase(GetMaterialMap getMaterialMap){
        return getMaterialMap;
    }

    @Provides
    public MaterialPriceMapCase provideMaterialPriceMapCase(GetMaterialPriceMap getMaterialPriceMap){
        return getMaterialPriceMap;
    }


}
