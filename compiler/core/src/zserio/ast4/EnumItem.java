package zserio.ast4;

import java.math.BigInteger;

import org.antlr.v4.runtime.Token;

/**
 * AST node for items defined by enumeration types.
 */
public class EnumItem extends AstNodeBase
{
    /**
     * Constructor.
     *
     * @param token           ANTLR4 token to localize AST node in the sources.
     * @param name            Name of the enumeration item.
     * @param valueExpression Expression value of the enumeration item.
     */
    public EnumItem(Token token, String name, Expression valueExpression)
    {
        super(token);

        this.name = name;
        this.valueExpression = valueExpression;
    }

    @Override
    public void accept(ZserioAstVisitor visitor)
    {
        visitor.visitEnumItem(this);
    }

    @Override
    public void visitChildren(ZserioAstVisitor visitor)
    {
        if (valueExpression != null)
            valueExpression.accept(visitor);
    }

    /**
     * Gets the name of enumeration item.
     *
     * @return Returns name of enumeration item.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets enumeration item value expression.
     *
     * @return Enumeration item value expression or null if value expression has not been specified.
     */
    public Expression getValueExpression()
    {
        return valueExpression;
    }

    /**
     * Gets the enumeration type which is owner of the enumeration item.
     *
     * @return Returns enumeration type which is owner of the enumeration item.
     */
    public EnumType getEnumType()
    {
        return enumType;
    }

    /**
     * Gets the integer value which represents the enumeration item.
     *
     * @return Returns the integer value of the enumeration item.
     */
    public BigInteger getValue()
    {
        return value;
    }

    /**
     * Sets the enumeration type which is owner of the enumeration item.
     *
     * @param enumType Owner to set.
     */
    protected void setEnumType(EnumType enumType)
    {
        this.enumType = enumType;
    }

    /**
     * Evaluates enumeration item value expression.
     *
     * @param defaultValue Enumeration item value to use if value expression has not been specified.
     */
    protected void evaluateValueExpression(BigInteger defaultValue)
    {
        if (valueExpression != null)
        {
            // there is a value for this enumeration item => evaluate and check value expression
            valueExpression.evaluate();

            if (valueExpression.getExprType() != Expression.ExpressionType.INTEGER)
                throw new ParserException(valueExpression, "Enumeration item '" + getName() +
                        "' has non-integer value!");
            value = valueExpression.getIntegerValue();
        }
        else
        {
            value = defaultValue;
        }
    }

    private final String name;
    private final Expression valueExpression;

    private EnumType enumType = null;
    private BigInteger value = null;
}
