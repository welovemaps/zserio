package zserio.extension.doc;

import java.util.ArrayList;
import java.util.List;

import zserio.ast.Rule;
import zserio.ast.RuleGroup;
import zserio.extension.common.ZserioExtensionException;

/**
 * FreeMarker template data for rules in the package used by Package emitter.
 */
public class RulesTemplateData extends PackageTemplateDataBase
{
    public RulesTemplateData(PackageTemplateDataContext context, RuleGroup ruleGroup)
            throws ZserioExtensionException
    {
        super(context, ruleGroup);

        for (Rule rule : ruleGroup.getRules())
            rules.add(new RuleTemplateData(context, ruleGroup, rule));
    }

    public Iterable<RuleTemplateData> getRules()
    {
        return rules;
    }

    public static class RuleTemplateData
    {
        RuleTemplateData(PackageTemplateDataContext context, RuleGroup ruleGroup, Rule rule)
        {
            symbol = SymbolTemplateDataCreator.createData(context, ruleGroup, rule);
            docComments = new DocCommentsTemplateData(context, rule.getDocComments());
        }

        public SymbolTemplateData getSymbol()
        {
            return symbol;
        }

        public DocCommentsTemplateData getDocComments()
        {
            return docComments;
        }

        private final SymbolTemplateData symbol;
        private final DocCommentsTemplateData docComments;
    }

    private final List<RuleTemplateData> rules = new ArrayList<RuleTemplateData>();
}
