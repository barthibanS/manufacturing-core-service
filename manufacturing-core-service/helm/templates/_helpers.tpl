{{/* Generate a fullname for resources */}}
{{- define "manufacturing-core-service.fullname" -}}
{{- printf "%s-%s" .Release.Name .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/* Chart name as a label */}}
{{- define "manufacturing-core-service.name" -}}
{{- .Chart.Name -}}
{{- end -}}

{{/* Chart version as a label */}}
{{- define "manufacturing-core-service.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" -}}
{{- end -}} 