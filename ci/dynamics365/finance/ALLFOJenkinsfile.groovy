node('e2e') {
    stage('invoke_general_regression') {
        build job: 'MSD365FO_General_Regression', propagate: false, wait: true
    }
    stage('invoke_AR_regression_tests') {
        build job: 'MSD365FO_AR_Regression', propagate: false, wait: true
    }
    stage('invoke_AP_regression_tests') {
        build job: 'MSD365FO_AP_Regression', propagate: false, wait: true
    }
    stage('invoke_VAT_regression_tests') {
        build job: 'MSD365FO_VAT_regression', propagate: false, wait: true
    }
    stage('invoke_integration_tests') {
        build job: 'MSD365Integration', propagate: false, wait: false
    }
}