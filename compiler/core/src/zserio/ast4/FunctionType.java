package zserio.ast4;

import java.util.Set;

import org.antlr.v4.runtime.Token;

import zserio.tools.ZserioToolPrinter;

/**
 * AST node for Function types.
 *
 * Function types are Zserio types as well.
 */
public class FunctionType extends AstNodeBase implements ZserioType
{
    /**
     * Constructor.
     *
     * @param token            ANTLR4 token to localize AST node in the sources.
     * @param pkg              Package to which belongs the function type.
     * @param returnType       Zserio type of the function return value.
     * @param name             Name of the function type.
     * @param resultExpression Result expression of the function type.
     */
    public FunctionType(Token token, Package pkg, ZserioType returnType, String name,
            Expression resultExpression)
    {
        super(token);

        this.pkg = pkg;
        this.returnType = returnType;
        this.name = name;
        this.resultExpression = resultExpression;
    }

    @Override
    public void accept(ZserioAstVisitor visitor)
    {
        visitor.visitFunction(this);
    }

    @Override
    public void visitChildren(ZserioAstVisitor visitor)
    {
        returnType.accept(visitor);
        resultExpression.accept(visitor);
    }

    @Override
    public Package getPackage()
    {
        return pkg;
    }

    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Gets unresolved function return Zserio type.
     *
     * @return Unresolved Zserio type defining the function return type.
     */
    public ZserioType getReturnType()
    {
        return returnType;
    }

    /**
     * Gets expression which represents function result.
     *
     * @return Function result expression.
     */
    public Expression getResultExpression()
    {
        return resultExpression;
    }

    @Override
    protected void evaluate()
    {
        // check result expression type
        final ZserioType resolvedTypeReference = TypeReference.resolveType(returnType);
        final ZserioType resolvedReturnType = TypeReference.resolveBaseType(resolvedTypeReference);
        ExpressionUtil.checkExpressionType(resultExpression, resolvedReturnType);

        // check usage of unconditional optional fields (this is considered as a warning)
        if (!resultExpression.containsFunctionCall() && !resultExpression.containsTernaryOperator())
        {
            final Set<Field> referencedFields = resultExpression.getReferencedSymbolObjects(Field.class);
            for (Field referencedField : referencedFields)
            {
                if (referencedField.getIsOptional())
                    ZserioToolPrinter.printWarning(resultExpression, "Function '" + name + "' contains " +
                            "unconditional optional fields.");
            }
        }
    }

    private final Package pkg;
    private final ZserioType returnType;
    private final String name;
    private final Expression resultExpression;
}
