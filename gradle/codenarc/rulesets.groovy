ruleset {
    ruleset('rulesets/basic.xml')
    ruleset('rulesets/braces.xml')
    ruleset('rulesets/comments.xml') {
        ClassJavadoc {
            enabled = false
        }
    }
    ruleset('rulesets/convention.xml') {
        VariableTypeRequired {
            enabled = false
        }
        CompileStatic {
            enabled = false
        }
        ImplicitClosureParameter {
            enabled = false
        }
        ImplicitReturnStatement {
            enabled = false
        }
        NoDef {
            enabled = false
        }
        MethodReturnTypeRequired {
            doNotApplyToClassNames = 'org.aa.branch.mapping.*'
        }
    }
    ruleset('rulesets/design.xml') {
        AbstractClassWithPublicConstructor {
            enabled = false
        }
        AbstractClassWithoutAbstractMethod {
            enabled = false
        }
        CloneableWithoutClone {
            enabled = false
        }
        BuilderMethodWithSideEffects
        Instanceof {
            enabled = false
        }
    }
    ruleset('rulesets/dry.xml')
    ruleset('rulesets/exceptions.xml')
    ruleset('rulesets/formatting.xml') {
        ClassEndsWithBlankLine {
            enabled = false
        }
        ClassStartsWithBlankLine {
            enabled = false
        }
        FileEndsWithoutNewline {
            enabled = false
        }
        SpaceAfterOpeningBrace {
            ignoreEmptyBlock = true
        }
        SpaceBeforeClosingBrace {
            ignoreEmptyBlock = true
        }
        SpaceAroundMapEntryColon {
            characterAfterColonRegex = /\s/
            characterBeforeColonRegex = /.*/
        }
        LineLength {
            length = 200
        }
    }
    ruleset('rulesets/generic.xml')
    ruleset('rulesets/groovyism.xml')
    ruleset('rulesets/imports.xml')
    ruleset('rulesets/logging.xml')
    ruleset('rulesets/naming.xml') {
        ConfusingMethodName {
            enabled = false
        }
        FactoryMethodName {
            enabled = false
        }
        MethodName {
            doNotApplyToClassNames = 'org.aa.branch.mapping.*'
        }
    }
    ruleset('rulesets/security.xml') {
        JavaIoPackageAccess {
            enabled = false
        }
    }
    ruleset('rulesets/unnecessary.xml')
    ruleset('rulesets/unused.xml')
}