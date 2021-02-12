#!/bin/bash

if ! (command -v ktlint)
then
    echo "Installing ktlint..."
    brew install ktlint
else
    echo ">> ktlint is already installed but we'll try to remove and re-install"
    brew uninstall ktlint
    brew install ktlint
    echo ">> ktlint is all set up!"
fi

echo ">> Installing git pre-commit hook"
ktlint installGitPreCommitHook
echo ">> Git pre-commit hook installed!"

echo ">> Installing git pre-push hook"
ktlint installGitPrePushHook
echo ">> Git pre-push hook installed!"

echo "------"

echo ">> Applying ktlint to IDEA!"
ktlint --android applyToIDEA -y
echo ">> ktlint applied to IDEA!"
