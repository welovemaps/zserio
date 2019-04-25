package zserio.ast4;

import java.util.List;

import org.antlr.v4.runtime.Token;

/**
 * AST node for Union types.
 *
 * Union types are Zserio types as well.
 */
public class UnionType extends CompoundType
{
    /**
     * Constructor.
     *
     * @param token      ANTLR4 token to localize AST node in the sources.
     * @param pkg        Package to which belongs the union type.
     * @param name       Name of the union type.
     * @param parameters List of parameters for the union type.
     * @param fields     List of all fields of the union type.
     * @param functions  List of all functions of the union type.
     */
    public UnionType(Token token, Package pkg, String name, List<Parameter> parameters, List<Field> fields,
            List<FunctionType> functions)
    {
        super(token, pkg, name, parameters, fields, functions);
    }

    @Override
    public void accept(ZserioAstVisitor visitor)
    {
        visitor.visitUnionType(this);
    }

    @Override
    protected void evaluate()
    {
        checkTableFields();
    }
};
