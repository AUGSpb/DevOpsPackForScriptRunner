package com.onresolve.scriptrunner.canned.jira.workflow.postfunctions

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.security.roles.ProjectRoleManager
import com.onresolve.scriptrunner.canned.CannedScript
import com.onresolve.scriptrunner.canned.jira.utils.CannedScriptUtils
import com.onresolve.scriptrunner.canned.jira.utils.DescriptionFormatter
import com.onresolve.scriptrunner.canned.util.BuiltinScriptErrors
import org.apache.log4j.Logger

class TestPF implements CannedScript {

    def log = Logger.getLogger(TestPF.class)
    def projectRoleManager = ComponentAccessor.getComponent(ProjectRoleManager.class)

    public static String FIELD_ROLE_ID = "FIELD_ROLE_ID"
    public static String FIELD_BOOLEAN_PARAM = "FIELD_BOOLEAN_PARAM"


    @Override
    String getName() {
        "Simple Post Function Example With Params"
    }

    @Override
    String getDescription() {
        "Simple Post Function Example With Params Description"
    }

    @Override
    List getCategories() {
        ["Function"]
    }


    @Override
    List getParameters(Map params) {
        [
                [
                        Label: "Role",
                        Name: FIELD_ROLE_ID,
                        Type: "list",
                        Values: CannedScriptUtils.getAllRoles(false),
                        Description: "Role param selector",
                ],
                [
                        Label: "Bool param",
                        Name: FIELD_BOOLEAN_PARAM,
                        Type: "bool",
                        Description: "Include some bool param",
                ],
        ]
    }

    @Override
    BuiltinScriptErrors doValidate(Map<String, String> params, boolean forPreview) {
        null
    }

    @Override
    Map doScript(Map<String, Object> params) {
        // todo: make some logic here
        def issue = params["issue"] as MutableIssue

        // Exmaple of getting PARAM:
        // params[FIELD_BOOLEAN_PARAM] = null


        params
    }

    @Override
    String getDescription(Map<String, String> params, boolean forPreview) {
        def projectRoleId = params[FIELD_ROLE_ID] as Long
        def role = projectRoleManager.getProjectRole(projectRoleId)

        def sb = new StringBuilder()
        sb << "Role parameter is: <b>${role.name}</b>"
        if (params[FIELD_BOOLEAN_PARAM]) {
            sb << ", bool param true"
        }
        sb << "."
        def customDescription = sb.toString()
        DescriptionFormatter.getDescription(
                params,
                getName(),
                forPreview,
                customDescription)
    }

    @Override
    Boolean isFinalParamsPage(Map params) {
        true
    }
}
