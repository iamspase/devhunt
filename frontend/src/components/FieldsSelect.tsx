import * as React from "react"
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

const fields = [
    "Software Development &  IT", 
    "Graphic Design",
    "Bussiness and Accounting",
    "Freelancing",
]

export function FieldsSelect() {
  return (
    <Select>
      <SelectTrigger className="w-full">
        <SelectValue placeholder="Select a fruit" />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectLabel>Fields</SelectLabel>
            {fields.map(field => 
                <SelectItem value={field}>{field}</SelectItem>
            )}
        </SelectGroup>
      </SelectContent>
    </Select>
  )
}