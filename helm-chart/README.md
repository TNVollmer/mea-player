
# Things to Change

In helm-chart/Chart.yaml `name` to your player-name

In helm-chart/templates/_helper.tpl replace all occurs of `player-skeleton-java-springboot` with your player-name

In /helm-chart/templates/* replace all occurs of `player-skeleton-java-springboot` in `include-block` with your player-name 

Update in helm-chart/Chart.yaml the `version` everytime you edit your helm chart

Update helm-chart/values.yaml on all `TODO-Tags`

# install on minikube

replace `player-skeleton-java-springboot` with your player name
replace `my-namespace` with your namespace

inside in /helm-chart
1. check of syntax errors `helm lint`
2. check output `helm template player-skeleton-java-springboot .`
3. dry to install `helm -n my-namespace install player-skeleton-java-springboot . --create-namespace --dry-run`
4. install `helm -n my-namespace install player-skeleton-java-springboot . --create-namespace`

or in root DIR 
1. check of syntax errors `helm lint helm-chart`
2. check output `helm template player-skeleton-java-springboot helm-chart`
3. dry to install `helm -n my-namespace install player-skeleton-java-springboot helm-chart --create-namespace --dry-run`
4. install `helm -n my-namespace install player-skeleton-java-springboot helm-chart --create-namespace`

---
Delete Release: `helm -n my-namespace delete player-skeleton-java-springboot`
