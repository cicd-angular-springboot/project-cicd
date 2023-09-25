import devops.cicd.tools.Ssh

def call() {
    node {
        def nodejsInstallation = tool name: 'nodejs', type: 'jenkins.plugins.nodejs.tools.NodeJSInstallation'
        env.PATH = "${nodejsInstallation}/bin:${env.PATH}"
        
        def mavenInstallation = tool 'maven-3.8.3'
        def mvnCmd = "${mavenInstallation}/bin/mvn"
        
        stage ('Pull Code') {
            git 'https://github.com/cicd-angular-springboot/FrontEnd-Angular.git'
        }
        
        stage('Test Maven') {
            sh "${mvnCmd} -v"
        }

        stage('Install Packages') {
            sh 'npm install && ls -lah'
        }

        stage('Test & Build Image') {
            sh "docker build -t ngochung1809/front-end-angular${env.BUILD_NUMBER} ."
        }

        stage('Deploy') {
            sh 'docker ps'
        }
    }
}

def executeDir(dir, command) {
    return "'cd ${dir} && ${command}'"
}
