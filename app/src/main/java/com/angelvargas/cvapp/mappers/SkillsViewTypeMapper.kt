package com.angelvargas.cvapp.mappers

import com.angelvargas.cvapp.adapters.viewtypes.SkillsViewType
import com.angelvargas.cvapp.domain.models.SkillsData
import com.angelvargas.data.mappers.Transform

class SkillsViewTypeMapper: Transform<SkillsData, SkillsViewType>() {

    override fun transform(value: SkillsData): SkillsViewType {
        return SkillsViewType(value.name, value.level, value.keywords)
    }
}