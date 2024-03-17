#!/bin/bash

GITHUB_TOKEN=$(sed -n 's/^ *GITHUB_TOKEN *= *"\(.*\)"/\1/p' local.properties)

./gradlew :core:apollo:downloadApolloSchema --endpoint='https://api.github.com/graphql' --schema=core/apollo/src/main/graphql/api/schema.graphqls --header="Authorization: Bearer $GITHUB_TOKEN"

if [ $? -eq 0 ]; then
    echo "スキーマのダウンロードが成功しました。"
else
    echo "スキーマのダウンロード中にエラーが発生しました。"
fi
