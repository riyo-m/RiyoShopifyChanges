node('e2e') {
    stage('invoke_smoke') {
        build job: 'MSD365_NextAUTOTEST_Smoke', propagate: false, wait: true
    }
    stage('invoke_general_regression') {
        build job: 'MSD365_NextAUTOTEST_Regression', propagate: false, wait: true
    }
    stage('invoke_AR_regression_tests') {
        build job: 'MSD365FO_NextAUTOTEST_AR_Regression', propagate: false, wait: true
    }
    stage('invoke_AP_regression_tests') {
        build job: 'MSD365FO_NextAUTOTEST_AP_Regression', propagate: false, wait: true
    }
    stage('invoke_VAT_regression_tests') {
        build job: 'MSD365FO_NextAUTOTEST_VAT_regression', propagate: false, wait: true
    }
    stage('invoke_integration_tests') {
        build job: 'MSD365_NextAUTOTEST_Integration', propagate: false, wait: false
    }
}