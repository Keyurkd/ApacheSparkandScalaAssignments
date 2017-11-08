var df:DataFrame = mySpark.read
      .format("com.crealytics.spark.excel")
      .option("sheetName", "Sheet1") // Required
      .option("useHeader", "true") // Required
      .option("treatEmptyValuesAsNulls", "false") // Optional, default: true
      .option("inferSchema", "false") // Optional, default: false
      .option("addColorColumns", "true") // Optional, default: false
      .option("startColumn", 0) // Optional, default: 0
      .option("endColumn", 10) // Optional, default: Int.MaxValue
      .option("timestampFormat", "MM-dd-yyyy HH:mm:ss") // Optional, default: yyyy-mm-dd hh:mm:ss[.fffffffff]
      //      .schema(employee) // Optional, default: Either inferred schema, or all columns are Strings
      .load("src/main/resources/datasets.xlsx").toDF()

val counts = df.filter("left == 1").groupBy("time_spend_company (Years)").count()

println(counts.show())
