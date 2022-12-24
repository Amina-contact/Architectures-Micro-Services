# Architectures-Micro-Services
# Devoir 1:Customer-Service & Billing-Service & Running Microservices dans un Docker Container
Mise en oeuvre d'une application distribuée basée sur deux micro-services en utilisant les bonnes pratiques  :
  - Couches DA0, Service, Web, DTO
  - Utilisation de MapStruct pour le mapping entre les objet Entities et DTO
  - Génération des API-DOCS en utilisant SWAGGER3 (Open API)
  - Communication entre micro-services en utilisant OpenFeign
  - Spring Cloud Gateway
  - Eureka Discovery Service
  
Déployer des microservices de démarrage à ressort dans un conteneur docker, et orchestrer ce conteneur docker à l'aide de docker compose pour exécuter plusieurs microservices sur un seul réseau, principalement pour permettre des communications de service à service.
# Devoir 2 :Web services SOAP, WSDL, UDDI avec JAXWS
1. Créer un Web service qui permet de : 
    - Convertir un montant de l’auro en DH
    - Consulter un Compte
    - Consulter une Liste de comptes
2. Déployer le Web service avec un simple Serveur JaxWS
3. Consulter et analyser le WSDL avec un Browser HTTP
4. Tester les opérations du web service avec un outil
   - comme SoapUI ou Oxygen
5. Créer un Client SOAP Java
# Devoir 3 :Architectures Micro services avec (Spring Cloud Config, Consul Discovery, Consul Config,Vault)
Créer une application de e-commerce basée sur les micro services :
1. Consul Discovery
2. Spring Cloud Config
3. Spring Cloud Gateway
4. Customer-service
5. Inventory Service
6. Order Service
7. Consul Config (Billing Service)
8. Vault (Billing Service)
9. Frontend Web avec Angular
# Devoir 4:Sécurité des micro services avec Keycloak
Partie 1 : 
1. Télécharger Keycloak 19
2. Démarrer Keycloak
3. Créer un compte Admin
4. Créer une Realm
5. Créer un client à sécuriser
6. Créer des utilisateurs
7. Créer des rôles
8. Affecter les rôles aux utilisateurs
9. Avec PostMan :
    - Tester l'authentification avec le mot de passe
    - Analyser les contenus des deux JWT Access Token et Refresh Token
    - Tester l'authentification avec le Refresh Token
    - Tester l'authentification avec Client ID et Client Secret
    - Changer les paramètres des Tokens Access Token et Refresh Token

Partie  2 :
   -Sécuriser L'architecture Micro services Du projet Customer-service, Inventory-service et Order-service
# Bank-Account-Service
Premier Micro-service avec REST API et GraphQL API.
