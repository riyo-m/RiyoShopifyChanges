node('e2e') {
    stage('invoke_smoke_tests') {
        build job: 'MSD365BC_smoke', propagate: false, wait: true
    }
    stage('invoke_general_regression') {
        build job: 'MSD365BC_regression', propagate: false, wait: true
    }
    stage('invoke_AP_regression_tests') {
        build job: 'MSD365BC_AP_Regression', propagate: false, wait: true
    }
    stage('invoke_Sales_regression_tests') {
        build job: 'MSD365BC_Sales_regression', propagate: false, wait: true
    }
}