#!/bin/bash

# detekt - Kotlin Static Code Analysis
# Detects code smells, complexity issues, and potential bugs

set -e

echo "Running detekt analysis..."
echo ""

RESULTS_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/results"
mkdir -p "$RESULTS_DIR"

PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")/../CocktailsDbApp" && pwd)"
CONFIG_FILE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/detekt-config.yml"

echo "Analyzing: CocktailsDbApp"
echo "Results: detekt/results/"
echo ""

echo "Running detekt..."

detekt \
    --config "$CONFIG_FILE" \
    --input "$PROJECT_PATH" \
    --report html:"$RESULTS_DIR/detekt_report.html" \
    --report md:"$RESULTS_DIR/detekt_report.md"  > /dev/null 2>&1 || true

echo ""
echo "detekt analysis completed"
echo "Reports generated:"
echo "   - detekt_report.html"
echo "   - detekt_report.md"
