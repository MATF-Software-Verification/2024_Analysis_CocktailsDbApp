#!/bin/bash

# ktlint - Kotlin Code Style Checker
# Analyzes Kotlin code formatting and style violations

set -e

echo "Running ktlint analysis..."
echo ""

RESULTS_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/results"
mkdir -p "$RESULTS_DIR"

PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")/../CocktailsDbApp" && pwd)"

echo "Analyzing: CocktailsDbApp"
echo "Results: ktlint/results/"
echo ""

echo "Running ktlint..."
ktlint $(find "$PROJECT_PATH" -type f -name "*.kt" \
  ! -path "*/build/*" \
  ! -path "*/generated/*" \
  ! -path "*/databinding/*" \
  ! -path "*/hilt_aggregated_deps/*") \
  --reporter=html,output="$RESULTS_DIR/ktlint_report.html" \
  --reporter=plain,output="$RESULTS_DIR/ktlint_report.txt" \
  > /dev/null 2>&1 || true

echo ""
echo "ktlint analysis completed"
echo "Reports generated:"
echo "   - ktlint_report.html"
echo "   - ktlint_report.txt"