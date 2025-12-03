# DOU Survey Data Analysis Tool

## Project Information
**Author**: Boroday Andriy Stanislavovych  
**Date**: 03.11.2025  
**JAR Location**: `dou_analysis_jar/dou-analysis.jar`  
**Java Version**: JDK 21+ (tested with JDK 25)

## Project Overview

This project is a comprehensive data analysis tool for processing and analyzing survey data from the DOU (Ukrainian IT community) platform. The application performs analytical tasks on synthetic survey data including filtering, grouping, statistical analysis, and outlier detection.

### Main Features
- **Synthetic Data Generation**: Infinite stream of realistic Person objects
- **Advanced Filtering**: City-based filtering with skip functionality
- **Statistical Analysis**: Income statistics calculation (min, max, average, std deviation)
- **Outlier Detection**: IQR (Interquartile Range) method implementation
- **Flexible CLI**: Command-line interface with configurable parameters

## Project Structure

```
src/main/java/org/example/
├── Main.java                    # Main application class
├── model/
│   └── Person.java             # Data model class
├── generator/
│   └── PersonGenerator.java    # Infinite stream generator
├── gatherer/
│   └── SkipByCityGatherer.java # Custom data collector
├── collector/
│   ├── IncomeStatisticsCollector.java # Custom statistics collector
│   └── IncomeStatistics.java   # Statistics data class
└── analysis/
    └── OutlierAnalyzer.java    # Outlier detection utility
```

## Prerequisites

### Required Software
- **Java Development Kit (JDK) 21 or higher** (JDK 25 recommended)

## Running the Application

### JAR File Location
The executable JAR file is located at:  
`dou_analysis_jar/dou-analysis.jar`

### Basic Usage (Default Parameters)
```bash
java -jar dou_analysis_jar/dou-analysis.jar
```

This will run the analysis with default parameters:
- **City**: Kyiv
- **Skip first**: 50 residents
- **Collect**: 500 residents
- **Age range**: 25-50 years

### Custom Parameters Examples

```bash
# Full argument names
java -jar dou_analysis_jar/dou-analysis.jar --city Lviv --skip 20 --limit 300

# Short argument names
java -jar dou_analysis_jar/dou-analysis.jar -c Odesa -s 10 -l 150 --min-age 30 --max-age 60
```

### Available Command-line Arguments

| Argument | Short Form | Description | Default |
|----------|------------|-------------|---------|
| `--city CITY` | `-c CITY` | City for filtering | "Kyiv" |
| `--skip COUNT` | `-s COUNT` | Number of matching persons to skip | 50 |
| `--limit COUNT` | `-l COUNT` | Maximum persons to collect | 500 |
| `--min-age AGE` | | Minimum age for filtering | 25 |
| `--max-age AGE` | | Maximum age for filtering | 50 |
| `--help` | `-h` | Display help information | N/A |

### Example Scenarios

```bash
# Analyze data from Lviv with custom parameters
java -jar dou_analysis_jar/dou-analysis.jar --city Lviv --skip 30 --limit 200

# Quick analysis with short form arguments
java -jar dou_analysis_jar/dou-analysis.jar -c Odesa -s 10 -l 100

# Get help information
java -jar dou_analysis_jar/dou-analysis.jar --help
```

## Application Output

The application generates comprehensive output including:

### 1. Initial Configuration
Displays used parameters (either default or provided)

### 2. Analysis Steps
- **Step 1: Data Collection** - Shows how many persons were collected
- **Step 2: Age Filtering** - Shows filtered count by age range
- **Step 3: Grouping** - Shows groups by first name with top 5 groups
- **Step 4: Income Statistics** - Shows min, max, average, and standard deviation
- **Step 5: Outlier Analysis** - Shows normal values, outliers, and percentage

### 3. Sample Data
Displays first 5 collected records for verification

### Example Output
```
=== DOU Survey Analysis ===
Using default values:
  City: Kyiv
  Skip first: 50
  Collect maximum: 500
  Age range: 25-50 years

--- Step 1: Data Collection ---
Filtering by city: Kyiv
Skipping first 50 residents of Kyiv
Collected 500 residents of Kyiv (skipped first 50)

--- Step 2: Age Filtering ---
Age range: 25-50 years
After age filtering: 380 persons
```

## Testing

### Manual Testing Instructions
1. Run with default parameters to verify basic functionality
2. Test with various city names from available cities list
3. Test edge cases (skip=0, limit=1, extreme age ranges)
4. Verify error handling with invalid parameters

### Available Cities for Testing
- Kyiv, Lviv, Odesa, Dnipro, Kharkiv

### Expected Behaviors
- Empty result when city doesn't exist in generated data
- Immediate termination when limit reached
- Proper error messages for invalid numeric inputs
- Consistent output formatting

## Troubleshooting

### Common Issues and Solutions

| Issue | Solution |
|-------|----------|
| "Error: numeric argument has invalid format" | Ensure numeric arguments are valid integers |
| "Error: limit must be greater than 0" | Use positive integers for limit parameter |
| "Error: invalid age range" | Ensure min-age ≤ max-age, both non-negative |
| Java Version Issues | Verify with `java -version`, requires JDK 21+ |
| "No such file or directory" | Ensure JAR file path is correct |
| "Could not find or load main class" | Check Java installation and JAR integrity |

### Verification Commands
```bash
# Check Java version
java -version

# Check if JAR file exists
ls -la dou_analysis_jar/dou-analysis.jar

# Run with absolute path if needed
java -jar /full/path/to/dou_analysis_jar/dou-analysis.jar
```

## Typical Usage Examples

### Scenario 1: Quick Analysis
```bash
# Navigate to project directory and run
java -jar dou_analysis_jar/dou-analysis.jar
```

### Scenario 2: Custom City Analysis
```bash
java -jar dou_analysis_jar/dou-analysis.jar --city Lviv --skip 20 --limit 300
```

### Scenario 3: Different Age Range
```bash
java -jar dou_analysis_jar/dou-analysis.jar --min-age 30 --max-age 45
```

### Scenario 4: Minimal Collection
```bash
java -jar dou_analysis_jar/dou-analysis.jar --skip 0 --limit 10
```

## Output Interpretation

### Statistics Explanation
- **min/max**: Minimum and maximum monthly income in UAH
- **avg**: Average monthly income
- **std**: Standard deviation (measure of income spread)
- **Outliers**: Data points significantly different from others (based on IQR method)

### Grouping Information
- Shows distribution of names in the dataset
- Helps understand demographic composition

## Important Notes

1. **Data Generation**: All data is randomly generated for demonstration
2. **Infinite Stream**: The generator creates an infinite stream, filtered by your parameters
3. **Memory Usage**: Application uses streaming, so memory usage is minimal
4. **Reproducibility**: Results vary each run due to random generation

## Support

For technical issues or questions regarding this application, please contact:
**Boroday Andriy Stanislavovych**

---
*Documentation updated: 03.11.2025*
