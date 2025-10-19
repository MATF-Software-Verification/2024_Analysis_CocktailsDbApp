#!/bin/bash

# Unit Tests - JUnit with Kover Coverage
# Runs unit tests and generates code coverage reports

set -e

echo "Running Unit Tests with Kover Coverage..."
echo ""

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
RESULTS_DIR="$SCRIPT_DIR/results"
PROJECT_PATH="$(cd "$(dirname "${BASH_SOURCE[0]}")/../CocktailsDbApp" && pwd)"

# Clean old results
rm -rf "$RESULTS_DIR"
mkdir -p "$RESULTS_DIR"

echo "Project: CocktailsDbApp"
echo "Results: junit/results/"
echo ""

cd "$PROJECT_PATH"

echo ""
./gradlew testDebugUnitTest koverHtmlReportDebug --rerun-tasks --no-daemon > "$RESULTS_DIR/test-output.log" 2>&1
TEST_EXIT_CODE=$?

# Copy test reports
echo ""
echo "Collecting test reports and coverage..."

# Copy unit test reports
if [ -d "app/build/reports/tests/testDebugUnitTest" ]; then
    cp -r app/build/reports/tests/testDebugUnitTest/* "$RESULTS_DIR/" 2>/dev/null || true
fi

# Copy Kover coverage reports (unit tests only)
if [ -d "app/build/reports/kover/htmlDebug" ]; then
    cp -r app/build/reports/kover/htmlDebug "$RESULTS_DIR/coverage" 2>/dev/null || true
fi

# Print test results summary
echo ""
echo "=== TEST RESULTS SUMMARY ==="
if [ $TEST_EXIT_CODE -eq 0 ]; then
    echo "ALL TESTS PASSED"
else
    echo "SOME TESTS FAILED (Exit code: $TEST_EXIT_CODE)"
fi

echo ""
echo "JUnit tests completed"
echo "Reports generated:"
echo "   - Test Results: $RESULTS_DIR/index.html"
echo "   - Coverage Report: $RESULTS_DIR/coverage/index.html"
