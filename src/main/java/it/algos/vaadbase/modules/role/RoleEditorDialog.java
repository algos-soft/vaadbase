/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package it.algos.vaadbase.modules.role;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.validator.StringLengthValidator;
import it.algos.vaadbase.ui.dialog.AbstractEditorDialog;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A dialog for editing {@link Role} objects.
 */
public class RoleEditorDialog extends AbstractEditorDialog<Role> {

    private final TextField roleNameField = new TextField("Role Code");

    public RoleEditorDialog(BiConsumer<Role, AbstractEditorDialog.Operation> itemSaver, Consumer<Role> itemDeleter) {
        super("Category", itemSaver, itemDeleter);

        addNameField();
    }

    private void addNameField() {
        getFormLayout().add(roleNameField);

        getBinder().forField(roleNameField)
                .withConverter(String::trim, String::trim)
                .withValidator(new StringLengthValidator(
                        "Role name must contain at least 3 printable characters",
                        3, null))
                .withValidator(
                        code -> RoleService.getInstance()
                                .findByKeyUnica(code) == null,
                        "Role name must be unique")
                .bind(Role::getCode, Role::setCode);
    }

    @Override
    protected void confirmDelete() {
//        int reviewCount = ReviewService.getInstance()
//                .findReviews(getCurrentItem().getName()).size();
//        if (reviewCount > 0) {
//            openConfirmationDialog(
//                    "Delete Category “" + getCurrentItem().getName() + "”?",
//                    "There are " + reviewCount
//                            + " reviews associated with this category.",
//                    "Deleting the category will mark the associated reviews as “undefined”."
//                            + "You may link the reviews to other categories on the edit page.");
//        }
    }
}
