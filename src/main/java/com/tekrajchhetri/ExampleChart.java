package com.tekrajchhetri;

import org.knowm.xchart.internal.chartpart.Chart;

public interface ExampleChart<C extends Chart<?, ?>> {

    C getChart();
}