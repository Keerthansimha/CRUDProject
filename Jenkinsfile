pipeline {
    agent {
        label 'ssh-agent'
    }
    
    options {
        timeout(time: 30, unit: 'MINUTES') // Global pipeline timeout
        buildDiscarder(logRotator(numToKeepStr: '5')) // Keep only last 5 builds
    }
    
    environment {
        // Java/Maven setup
        JAVA_HOME = '/opt/jdk-24'
        MAVEN_HOME = '/opt/maven'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${env.PATH}"
        
        // Database configuration (using Jenkins credentials)
        DB_HOST = 'your-mysql-host' // Replace with your MySQL server IP/hostname
        DB_PORT = '3306'
        DB_NAME = 'employee_directory'
        DB_URL = "jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useSSL=false&serverTimezone=UTC"
        DB_USER = credentials('MYSQL_CRUD_USER') // Create in Jenkins Credentials
        DB_PASSWORD = credentials('MYSQL_CRUD_PASSWORD') // Create in Jenkins Credentials
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/testing']],
                    extensions: [],
                    userRemoteConfigs: [[
                        credentialsId: 'github',
                        url: 'https://github.com/Keerthansimha/CRUDProject.git'
                    ]]
                ])
            }
        }

        stage('Clean') {
            steps {
                echo 'Cleaning the project workspace using Maven...'
                sh 'mvn clean'
            }
        }

        stage('Build') {
            options {
                timeout(time: 15, unit: 'MINUTES') // Stage-specific timeout
            }
            steps {
                echo 'Building the project using Maven...'
                sh 'mvn clean package -DskipTests' // Skip tests during build
            }
        }
        
        stage('Verify DB Connection') {
            steps {
                script {
                    try {
                        // Test database connectivity
                        echo "Testing connection to: ${DB_URL}"
                        sh """
                            mysql -h ${DB_HOST} -P ${DB_PORT} \
                            -u ${DB_USER} -p${DB_PASSWORD} \
                            -e 'USE ${DB_NAME}; SELECT 1;'
                        """
                    } catch (Exception e) {
                        error("Database connection failed: ${e.getMessage()}")
                    }
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution completed.'
        }
        success {
            echo 'Pipeline executed successfully.'
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }
}
