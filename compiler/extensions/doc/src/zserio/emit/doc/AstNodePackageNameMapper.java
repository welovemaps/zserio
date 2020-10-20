package zserio.emit.doc;

import zserio.ast.ArrayInstantiation;
import zserio.ast.AstNode;
import zserio.ast.BitmaskType;
import zserio.ast.ChoiceType;
import zserio.ast.Constant;
import zserio.ast.EnumType;
import zserio.ast.InstantiateType;
import zserio.ast.Package;
import zserio.ast.PackageName;
import zserio.ast.PubsubType;
import zserio.ast.ServiceType;
import zserio.ast.SqlDatabaseType;
import zserio.ast.SqlTableType;
import zserio.ast.StructureType;
import zserio.ast.Subtype;
import zserio.ast.TemplateArgument;
import zserio.ast.TypeInstantiation;
import zserio.ast.TypeReference;
import zserio.ast.UnionType;
import zserio.ast.ZserioAstDefaultVisitor;
import zserio.ast.ZserioType;

class AstNodePackageNameMapper
{
    public static PackageName getPackageName(AstNode node)
    {
        final PackageVisitor visitor = new PackageVisitor();
        node.accept(visitor);

        final Package pkg = visitor.getPackage();

        return (pkg != null) ? pkg.getPackageName() : null;
    }

    private static class PackageVisitor extends ZserioAstDefaultVisitor
    {
        public Package getPackage()
        {
            return pkg;
        }

        @Override
        public void visitConstant(Constant constant)
        {
            pkg = constant.getPackage();
        }

        @Override
        public void visitSubtype(Subtype subtype)
        {
            pkg = subtype.getPackage();
        }

        @Override
        public void visitStructureType(StructureType structureType)
        {
            pkg = structureType.getPackage();
        }

        @Override
        public void visitChoiceType(ChoiceType choiceType)
        {
            pkg = choiceType.getPackage();
        }

        @Override
        public void visitUnionType(UnionType unionType)
        {
            pkg = unionType.getPackage();
        }

        @Override
        public void visitEnumType(EnumType enumType)
        {
            pkg = enumType.getPackage();
        }

        @Override
        public void visitBitmaskType(BitmaskType bitmaskType)
        {
            pkg = bitmaskType.getPackage();
        }

        @Override
        public void visitSqlTableType(SqlTableType sqlTableType)
        {
            pkg = sqlTableType.getPackage();
        }

        @Override
        public void visitSqlDatabaseType(SqlDatabaseType sqlDatabaseType)
        {
            pkg = sqlDatabaseType.getPackage();
        }

        @Override
        public void visitServiceType(ServiceType serviceType)
        {
            pkg = serviceType.getPackage();
        }

        @Override
        public void visitPubsubType(PubsubType pubsubType)
        {
            pkg = pubsubType.getPackage();
        }

        @Override
        public void visitTypeReference(TypeReference typeReference)
        {
            final ZserioType type = typeReference.getType();
            if (type != null) // only if it isn't a template parameter
                type.accept(this);
        }

        @Override
        public void visitTypeInstantiation(TypeInstantiation typeInstantiation)
        {
            if (typeInstantiation instanceof ArrayInstantiation)
            {
                ((ArrayInstantiation)typeInstantiation).getElementTypeInstantiation()
                        .getTypeReference().accept(this);
            }
            else
            {
                typeInstantiation.getTypeReference().accept(this);
            }
        }

        @Override
        public void visitInstantiateType(InstantiateType templateInstantiation)
        {
            pkg = templateInstantiation.getPackage();
        }

        @Override
        public void visitTemplateArgument(TemplateArgument templateArgument)
        {
            templateArgument.getTypeReference().accept(this);
        }

        private Package pkg = null;
    }
}
