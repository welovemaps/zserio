package zserio.ast4;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import org.antlr.v4.runtime.Token;

import zserio.ast4.Package;
import zserio.ast4.Scope;
import zserio.ast4.ZserioScopedType;
import zserio.tools.HashUtil;

/**
 * AST abstract node for all compound types.
 *
 * This is an abstract class for all compound Zserio types (structure types, choice types, ...).
 */
public abstract class CompoundType extends AstNodeBase implements ZserioScopedType, Comparable<CompoundType>
{
    CompoundType(Token token, Package pkg, String name, List<Parameter> parameters, List<Field> fields,
            List<FunctionType> functions)
    {
        super(token);

        this.pkg = pkg;
        this.name = name;
        this.parameters = parameters;
        this.fields = fields;
        this.functions = functions;

        for (Field field : fields)
            field.setCompoundType(this);
    }

    @Override
    public int compareTo(CompoundType other)
    {
        final int result = getName().compareTo(other.getName());
        if (result != 0)
            return result;

        return getPackage().getPackageName().compareTo(other.getPackage().getPackageName());
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
            return true;

        if (other instanceof CompoundType)
            return compareTo((CompoundType)other) == 0;

        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = HashUtil.HASH_SEED;
        hash = HashUtil.hash(hash, getName());
        hash = HashUtil.hash(hash, getPackage().getPackageName());

        return hash;
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

    @Override
    public Iterable<ZserioType> getUsedTypeList()
    {
        Set<ZserioType> usedTypeSet = new LinkedHashSet<ZserioType>();
        //addFieldsToUsedTypeSet(usedTypeSet); // TODO:

        return usedTypeSet;
    }

    @Override
    public Scope getScope()
    {
        return scope;
    }

    /**
     * Sets package for the compound type.
     *
     * This method is called by code generated by ANTLR using TypeEvaluator.g.
     *
     * @param pkg Package to set.
     */
    public void setPackage(Package pkg)
    {
        this.pkg = pkg;
    }

    /**
     * Gets all fields associated to this compound type.
     *
     * Fields are ordered according to their definition in Zserio source file.
     *
     * @return List of fields which this compound type contains.
     */
    public List<Field> getFields()
    {
        return fields;
    }

    /**
     * Gets all parameters associated to this compound type.
     *
     * Parameters are ordered according to their definition in Zserio source file.
     *
     * @return List of parameters which this compound type contains.
     */
    public List<Parameter> getParameters()
    {
        return parameters;
    }

    /**
     * Gets all functions associated to this compound type.
     *
     * Functions are ordered according to their definition in Zserio source file.
     *
     * @return List of functions which this compound type contains.
     */
    /*public List<FunctionType> getFunctions()
    {
        return functions;
    }*/ // TODO:

    /**
     * Checks if this compound type contains itself as an optional none array field.
     *
     * This is called from C++ emitter during mapping of optional fields.
     *
     * @return true if this compound type contains optional recursion.
     */
    public boolean containsOptionalRecursion()
    {
        return containsOptionalRecursion;
    }

    /**
     * Checks if this compound type needs children initialization method.
     *
     * This is called from C++ emitter to check if the compound type has some descendant with parameters (if
     * (some descendant has initialize method).
     *
     * @return true if this compound type has some descendant with parameters.
     */
    public boolean needsChildrenInitialization()
    {
        /*for (Field field : fields)
        {
            final ZserioType fieldBaseType = TypeReference.resolveBaseType(field.getFieldReferencedType());
            if (fieldBaseType instanceof CompoundType)
            {
                final CompoundType childCompoundType = (CompoundType)fieldBaseType;
                // compound type can have itself as an optional field
                if (!childCompoundType.getParameters().isEmpty() ||
                        (childCompoundType != this && childCompoundType.needsChildrenInitialization()))
                    return true;
            }
        }*/ // TODO:

        return false;
    }

    /**
     * Checks if this compound type or any of its subfield contains some offset.
     *
     * @return true if this compound type contains some offset.
     */
    public boolean hasFieldWithOffset()
    {
        /*for (Field field : fields)
        {
            if (field.getOffsetExpr() != null)
                return true;

            final ZserioType fieldBaseType = TypeReference.resolveBaseType(field.getFieldReferencedType());
            if (fieldBaseType instanceof CompoundType)
            {
                final CompoundType childCompoundType = (CompoundType)fieldBaseType;
                // compound type can have itself as an optional field
                if (childCompoundType != this && childCompoundType.hasFieldWithOffset())
                    return true;
            }
        }*/ // TODO:

        return false;
    }

    /**
     * Gets list of compound types which use this compound type.
     *
     * @return List of compound types which use this compound type.
     */
    public Iterable<CompoundType> getUsedByCompoundList()
    {
        return usedByCompoundList;
    }

    /**
     * Gets list of service types which use this compound type.
     *
     * @return List of service types using this compound type.
     */
    /*public Iterable<ServiceType> getUsedByServiceList()
    {
        return usedByServiceList;
    }*/ // TODO:

    /**
     * Checks if this compound type is contained in the given compound type 'outer'.
     *
     * @return true if this compound type is contained in compound type 'outer'
     */
    public boolean isContainedIn(CompoundType outer)
    {
        return isContainedIn(outer, new Stack<CompoundType>());
    }

    /**
     * Gets the list of referenced Zserio types of given type.
     *
     * @param clazz  Zserio type to use.
     *
     * @return List of referenced Zserio types of given type.
     */
    /*public <T extends ZserioType> Set<T> getReferencedTypes(Class<? extends T> clazz)
    {
        final Set<T> result = new HashSet<T>();
        for (Field field : fields)
            result.addAll(field.getReferencedTypes(clazz));
        for (FunctionType function : functions)
            result.addAll(function.getResultExpression().getReferencedSymbolObjects(clazz));

        return result;
    }*/ // TODO:

    /**
     * Gets documentation comment associated to this compound type.
     *
     * @return Documentation comment token associated to this compound type.
     */
    /*public DocCommentToken getDocComment()
    {
        return (docComment != null) ? docComment : getHiddenDocComment();
    }*/ // TODO:

    protected void walkCompoundType(ZserioListener listener)
    {
        for (Parameter parameter : parameters)
            parameter.walk(listener);

        for (Field field : fields)
            field.walk(listener);
    }

    /*protected void addFieldsToUsedTypeSet(Set<ZserioType> usedTypeSet)
    {
        for (Field field : fields)
        {
            final ZserioType usedType = TypeReference.resolveType(field.getFieldReferencedType());
            if (!ZserioTypeUtil.isBuiltIn(usedType))
                usedTypeSet.add(usedType);
        }
    }*/ // TODO:

    /*protected void setDocComment(DocCommentToken docComment)
    {
        this.docComment = docComment;
    }*/

    /*protected void checkTableFields() throws ParserException
    {
        // check if fields are not sql tables
        for (Field field : fields)
        {
            ZserioType fieldBaseType = TypeReference.resolveBaseType(field.getFieldReferencedType());
            if (fieldBaseType instanceof SqlTableType)
                throw new ParserException(field, "Field '" + field.getName() +
                        "' cannot be a sql table!");
        }
    }*/ // TODO:

    /*@Override
    protected void check() throws ParserException
    {
        // check recursive fields which are not arrays
        containsOptionalRecursion = false;
        for (Field field : fields)
        {
            ZserioType fieldType = field.getFieldType();
            if (fieldType instanceof TypeInstantiation)
                fieldType = ((TypeInstantiation)fieldType).getReferencedType();
            fieldType = TypeReference.resolveBaseType(fieldType);

            if (fieldType == this)
            {
                // this field is not array and it is recursive
                if (field.getIsOptional())
                    containsOptionalRecursion = true;
                else
                    throw new ParserException(field, "Field '" + field.getName() +
                            "' is recursive and neither optional nor array!");
            }
        }

        // add use-by compound for subtypes needed for documentation emitter
        for (Field field : fields)
        {
            final ZserioType fieldReferencedType =
                    TypeReference.resolveType(field.getFieldReferencedType());
            if (fieldReferencedType instanceof Subtype)
                ((Subtype)fieldReferencedType).setUsedByCompound(this);
        }
        for (Parameter parameter : parameters)
        {
            final ZserioType parameterType = TypeReference.resolveType(parameter.getParameterType());
            if (parameterType instanceof Subtype)
                ((Subtype)parameterType).setUsedByCompound(this);
        }
        for (FunctionType function : functions)
        {
            final ZserioType functionType = TypeReference.resolveType(function.getReturnType());
            if (functionType instanceof Subtype)
                ((Subtype)functionType).setUsedByCompound(this);
        }
    }*/

    /**
     * Sets compound type which uses this compound type.
     *
     * @param compoundType Compound type to set.
     *
     * @throws Throws if circular containment occurs.
     */
    protected void setUsedByCompoundType(CompoundType compoundType) throws ParserException
    {
        // check for circular containment  TODO This is used by expressions
        if (compoundType.isContainedIn(this))
            throw new ParserException(this, "Circular containment between '" + getName() +
                                      "' and '" + compoundType.getName() + "'!");

        usedByCompoundList.add(compoundType);
    }

    /**
     * Sets service type which uses this compound type.
     *
     * @param serviceType Service type to set.
     */
    /*protected void setUsedByServiceType(ServiceType serviceType)
    {
        usedByServiceList.add(serviceType);
    }*/

    /**
     * The "is contained" relationship may contain cycles use a stack to avoid them. This is a simple DFS path
     * finding algorithm that finds a path from 'this' to 'outer'.
     */
    private boolean isContainedIn(CompoundType outer, Stack<CompoundType> seen)
    {
        if (usedByCompoundList.contains(outer))
        {
            return true;
        }

        // check whether any container of 'this' is contained in 'outer'
        for (CompoundType c : usedByCompoundList)
        {
            if (seen.search(c) == -1)
            {
                seen.push(c);
                if (c.isContainedIn(outer, seen))
                    return true;
                seen.pop();
            }
        }

        return false;
    }

    private final Scope scope = new Scope(this);
    private Package pkg;
    private String name;

    private final List<Field> fields;
    private final List<Parameter> parameters;
    private final List<FunctionType> functions;

    private boolean containsOptionalRecursion;

    private final SortedSet<CompoundType> usedByCompoundList = new TreeSet<CompoundType>();
    //private final SortedSet<ServiceType> usedByServiceList = new TreeSet<ServiceType>(); // TODO:
}
