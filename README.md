#!/bin/bash

# 2024_Analysis_CocktailsDbApp

## Project Description  
As part of this seminar paper, the project **CocktailsDbApp** was tested, and its source code is available at the following link:  
- **GitHub URL**: [https://github.com/stefan-hlw/CocktailsDbApp](https://github.com/stefan-hlw/CocktailsDbApp)  
- **Branch analyzed**: `main`  
- **Commit hash**: `3ceb0fcb8135c4ce81de034c13dad6109f583237`  

The project is a Kotlin-based Android application designed for cocktail enthusiasts. It provides a curated collection of recipes for both alcoholic and non-alcoholic drinks. Users can create personalized profiles and save their favorite cocktails for easy access, enhancing their mixology experience.

## Author  
**Dragana Zdravkovic**, mi231020

Contact: dragana.zdravkovic602@gmail.com

## Tools and Techniques Used

### 1. **ktlint** - Kotlin Code Style Checker (Formatter/Linter)
- **Purpose:** Analyzes code formatting, naming conventions, and style violations
- **Type:** Code formatting and style checking tool
- **Output:** Plain text and HTML reports showing style violations
- **Reproducibility:** Script: `ktlint/run_ktlint.sh`

### 2. **detekt** - Static Code Analysis for Kotlin
- **Purpose:** Detects code smells, complexity issues, and potential bugs
- **Type:** Static code analysis tool
- **Output:** HTML and MD reports with detailed findings
- **Reproducibility:** Script: `detekt/run_detekt.sh`

### 3. **Unit Tests + Kover** - Kotlin/Android Unit Testing with Code Coverage
- **Purpose:** Validates business logic and measures code test coverage
- **Type:** Unit Testing (different test category from UI tests)
- **Coverage Tool:** Kover (Kotlin-specific coverage tool, integrated into test infrastructure)
- **Output:** Test reports and coverage metrics
- **Reproducibility:** Script: `junit/run_tests.sh`

### 4. **Espresso** - Android Instrumented UI Testing
- **Purpose:** Tests UI interactions and Android-specific functionality
- **Type:** Instrumented/Integration Testing (different test category from unit tests)
- **Note:** This tool was NOT covered in course exercises
- **Requires:** Connected Android device or emulator
- **Output:** Android instrumented test reports
- **Reproducibility:** Script: `espresso/run_espresso.sh`

## Project Structure

```
2024_Analysis_CocktailsDbApp/
├── CocktailsDbApp/              # Original Android project (git submodule)
├── ktlint/                      # Code style checker
│   ├── run_ktlint.sh           # Script to run ktlint
│   └── results/                # ktlint analysis results
├── detekt/                      # Static code analysis
│   ├── run_detekt.sh           # Script to run detekt
│   ├── detekt-config.yml       # Detekt configuration
│   └── results/                # detekt analysis results
├── junit/                       # JUnit unit tests + Kover coverage
│   ├── run_tests.sh            # Script to run unit tests with coverage
│   └── results/                # Test reports and Kover coverage
├── espresso/                    # Espresso instrumented tests + JaCoCo coverage
│   ├── run_espresso.sh         # Script to run Espresso tests with coverage
│   └── results/                # Espresso test reports and JaCoCo coverage
├── .github/                    # CI/CD configuration
│   └── workflows/              # GitHub Actions workflows
├── ProjectAnalysisReport.md   # Detailed analysis findings
├── README.md                  # This file
└── .gitmodules               # Git submodule configuration
```

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Android Studio or Android SDK
- Gradle
- Homebrew (macOS) or package manager
- Git

### Installation Steps

1. **Clone and initialize:**
   ```bash
   git clone <repository-url>
   cd 2024_Analysis_CocktailsDbApp
   git submodule update --init --recursive
   ```

2. **Install required tools:**
   ```bash
   # Install ktlint and detekt
   brew install ktlint detekt
   
   # Ensure Java 17+ and Android SDK are installed
   ```

3. **Run analysis tools:**
   ```bash
   bash ktlint/run_ktlint.sh
   bash detekt/run_detekt.sh
   bash junit/run_tests.sh
   bash espresso/run_espresso.sh
   ```

## Running Tools

Run each tool individually:

```bash
bash ktlint/run_ktlint.sh           # Code style analysis
bash detekt/run_detekt.sh           # Static code analysis
bash junit/run_tests.sh             # Unit tests with Kover coverage
bash espresso/run_espresso.sh       # Instrumented tests with JaCoCo coverage
```

## Reports Location

All analysis reports are generated in tool-specific `results/` directories:

- **ktlint:** `ktlint/results/`
  - `ktlint_results.txt` - Text report
  - `ktlint_report.html` - HTML report

- **detekt:** `detekt/results/`
  - `detekt-report.html` - HTML report
  - `detekt-report.txt` - Text summary
  - `detekt-report.xml` - XML format

- **JUnit Tests:** `junit/results/`
  - Android unit tests
  - `index.html` - Test results HTML
  - `coverage/` - Kover coverage report

- **Espresso:** `espresso/results/`
  - Android instrumented test reports
  - `jacoco/` - JaCoCo coverage report
  - (Requires connected device/emulator)

## Reproducing Results

Each tool has a dedicated script that can be executed independently:

```bash
bash ktlint/run_ktlint.sh          # Kotlin code style analysis
bash detekt/run_detekt.sh          # Static code analysis
bash junit/run_tests.sh            # Unit tests + Kover coverage
bash espresso/run_espresso.sh      # Instrumented tests + JaCoCo coverage
```

All scripts are self-contained and generate reports in their respective `results/` directories.

---

**Author:** Dragana Zdravkovic (mi231020)  
**Project:** CocktailsDbApp
**Date:** October, 2025