package zserio.emit.common;

import zserio.ast4.ChoiceType;
import zserio.ast4.ConstType;
import zserio.ast4.EnumType;
import zserio.ast4.Import;
import zserio.ast4.Package;
import zserio.ast4.Root;
import zserio.ast4.ServiceType;
import zserio.ast4.SqlDatabaseType;
import zserio.ast4.SqlTableType;
import zserio.ast4.StructureType;
import zserio.ast4.Subtype;
import zserio.ast4.UnionType;

/**
 * An Emitter is a class that emits (i.e. generates) code while traversing the Abstract Syntax Tree
 * corresponding to a Zserio module.
 *
 * An Emitter is passed to a Tree Walker which traverses the AST and invokes methods of its Emitter when
 * entering or leaving subtrees of a given type.
 *
 * Thus, any kind of code can be generated by using different Emitter classes implementing this interface
 * together with the same Tree Walker.
 */
public interface Emitter4
{
    /**
     * Called when ROOT AST node begins.
     *
     * @param root Current ROOT AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginRoot(Root root) throws ZserioEmitException;

    /**
     * Called when ROOT AST node ends.
     *
     * @param root Current ROOT AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void endRoot(Root root) throws ZserioEmitException;

    /**
     * Called when PACKAGE AST node begins.
     *
     * @param translationUnit Current PACKAGE AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginPackage(Package pkg) throws ZserioEmitException;

    /**
     * Called when PACKAGE node ends.
     *
     * @param translationUnit Current PACKAGE AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void endPackage(Package pkg) throws ZserioEmitException;

    /**
     * Called when IMPORT AST node begins.
     *
     * @param importNode Current IMPORT AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginImport(Import importNode) throws ZserioEmitException;

    /**
     * Called when CONST AST node begins.
     *
     * @param constType Current CONST AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginConst(ConstType constType) throws ZserioEmitException;

    /**
     * Called when SUBTYPE AST node begins.
     *
     * @param subType Current SUBTYPE AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginSubtype(Subtype subType) throws ZserioEmitException;

    /**
     * Called when STRUCTURE AST node begins.
     *
     * @param structureType Current STRUCTURE AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginStructure(StructureType structureType) throws ZserioEmitException;

    /**
     * Called when CHOICE AST node begins.
     *
     * @param choiceType Current CHOICE AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginChoice(ChoiceType choiceType) throws ZserioEmitException;

    /**
     * Called when UNION AST node begins.
     *
     * @param unionType Current UNION AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginUnion(UnionType unionType) throws ZserioEmitException;

    /**
     * Called when ENUM AST node begins.
     *
     * @param enumType Current FUNCTION AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginEnumeration(EnumType enumType) throws ZserioEmitException;

    /**
     * Called when SQL_TABLE AST node begins.
     *
     * @param sqlTableType Current SQL_TABLE AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginSqlTable(SqlTableType sqlTableType) throws ZserioEmitException;

    /**
     * Called when SQL_DATABASE AST node begins.
     *
     * @param sqlDatabaseType Current SQL_DATABASE AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginSqlDatabase(SqlDatabaseType sqlDatabaseType) throws ZserioEmitException;

    /**
     * Called when SERVICE AST node begins.
     *
     * @param service Current SERVICE AST node.
     *
     * @throws In case of any internal error of the extension.
     */
    public void beginService(ServiceType service) throws ZserioEmitException;
}
